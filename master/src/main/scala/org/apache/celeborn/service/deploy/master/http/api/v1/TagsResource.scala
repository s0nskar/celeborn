/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.celeborn.service.deploy.master.http.api.v1

import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.celeborn.common.CelebornConf
import org.apache.celeborn.rest.v1.model.DynamicConfigResponse
import org.apache.celeborn.server.common.http.api.ApiRequestContext
import org.apache.celeborn.server.common.service.config.ConfigLevel
import org.apache.commons.lang3.StringUtils

import javax.ws.rs.{Consumes, GET, Path, Produces, QueryParam, ServiceUnavailableException}
import javax.ws.rs.core.MediaType

@Tag(name = "Tags")
@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
private[api] class TagsResource extends ApiRequestContext {
  private def configService = httpService.configService

  configService.getSystemConfigFromCache.getTags

  @ApiResponse(
    responseCode = "200",
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(implementation = classOf[DynamicConfigResponse]))),
    description = "List the dynamic configs. " +
      "The parameter level specifies the config level of dynamic configs. " +
      "The parameter tenant specifies the tenant id of TENANT or TENANT_USER level. " +
      "The parameter name specifies the user name of TENANT_USER level. " +
      "Meanwhile, either none or all of the parameter tenant and name are specified for TENANT_USER level.")
  @GET
  def tags: DynamicConfigResponse = {
    if (configService == null) {
      throw new ServiceUnavailableException(
        s"Dynamic configuration is disabled. Please check whether to config" +
          s" `${CelebornConf.DYNAMIC_CONFIG_STORE_BACKEND.key}`.")
    } else {
      new TagResponse()
      if (StringUtils.isEmpty(tag)) {
        new DynamicConfigResponse()
          .configs(ConfigLevel.values().flatMap { configLevel =>
            getDynamicConfig(configLevel.name(), tenant, name)
          }.toSeq.asJava)
      } else {
        new DynamicConfigResponse()
          .configs(getDynamicConfig(level, tenant, name).asJava)
      }
    }
  }
}
