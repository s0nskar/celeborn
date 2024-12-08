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

import java.util
import java.util.stream.Collectors
import javax.ws.rs.{Consumes, GET, Produces, QueryParam, ServiceUnavailableException}
import javax.ws.rs.core.MediaType

import scala.collection.JavaConverters.asJavaIterableConverter

import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.commons.lang3.StringUtils

import org.apache.celeborn.common.CelebornConf
import org.apache.celeborn.rest.v1.model.{TagResponse, TagsResponse}
import org.apache.celeborn.server.common.http.api.ApiRequestContext

@Tag(name = "Tags")
@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
private[api] class TagsResource extends ApiRequestContext {

  private def configService = httpService.configService

  @ApiResponse(
    responseCode = "200",
    content = Array(new Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = new Schema(implementation = classOf[TagsResponse]))),
    description = "List of the workers tags")
  @GET
  def getTags(
      @QueryParam("tag") tag: String): TagsResponse = {
    if (configService == null) {
      throw new ServiceUnavailableException(
        s"Dynamic configuration is disabled. Please check whether to config" +
          s" `${CelebornConf.DYNAMIC_CONFIG_STORE_BACKEND.key}`.")
    } else {
      val tagStore = configService.getSystemConfigFromCache.getTags
      val tagsResponse = new TagsResponse()

      if (tagStore == null) {
        return tagsResponse
      }

      val tagKeys =
        if (StringUtils.isEmpty(tag)) {
          tagStore.keySet()
        } else {
          tag.split(",").toSet.asJava
        }

      tagKeys.forEach(tag => {
        val w = tagStore.getOrDefault(tag, new util.HashSet[String]())
        tagsResponse.addTagsItem(new TagResponse()
          .tag(tag)
          .workerIds(w.stream().collect(Collectors.toList())))
      })
      tagsResponse
    }
  }
}
