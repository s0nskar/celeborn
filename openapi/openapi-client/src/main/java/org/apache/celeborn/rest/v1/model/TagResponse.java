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


package org.apache.celeborn.rest.v1.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TagResponse
 */
@JsonPropertyOrder({
  TagResponse.JSON_PROPERTY_TAG,
  TagResponse.JSON_PROPERTY_WORKER_IDS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class TagResponse {
  public static final String JSON_PROPERTY_TAG = "tag";
  private String tag;

  public static final String JSON_PROPERTY_WORKER_IDS = "workerIds";
  private List<String> workerIds = new ArrayList<>();

  public TagResponse() {
  }

  public TagResponse tag(String tag) {
    
    this.tag = tag;
    return this;
  }

  /**
   * The tag Name
   * @return tag
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TAG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTag() {
    return tag;
  }


  @JsonProperty(JSON_PROPERTY_TAG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTag(String tag) {
    this.tag = tag;
  }

  public TagResponse workerIds(List<String> workerIds) {
    
    this.workerIds = workerIds;
    return this;
  }

  public TagResponse addWorkerIdsItem(String workerIdsItem) {
    if (this.workerIds == null) {
      this.workerIds = new ArrayList<>();
    }
    this.workerIds.add(workerIdsItem);
    return this;
  }

  /**
   * The workerIds with the tag
   * @return workerIds
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_WORKER_IDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<String> getWorkerIds() {
    return workerIds;
  }


  @JsonProperty(JSON_PROPERTY_WORKER_IDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setWorkerIds(List<String> workerIds) {
    this.workerIds = workerIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagResponse tagResponse = (TagResponse) o;
    return Objects.equals(this.tag, tagResponse.tag) &&
        Objects.equals(this.workerIds, tagResponse.workerIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, workerIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagResponse {\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    workerIds: ").append(toIndentedString(workerIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

