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

package org.apache.celeborn.service.deploy.worker.congestcontrol;

import org.apache.celeborn.common.identity.UserIdentifier;
import org.apache.celeborn.common.metrics.source.AbstractSource;
import org.apache.celeborn.common.quota.UserTrafficQuota;
import org.apache.celeborn.service.deploy.worker.WorkerSource;

public class AppCongestionControlContext {

  private volatile boolean congestionControlFlag;

  private final String appId;

  private final BufferInfo appBufferInfo;

  private final BufferInfo userBufferInfo;

  private final BufferStatusHub workerBufferStatusHub;

  private final UserIdentifier userIdentifier;

  private volatile UserTrafficQuota userTrafficQuota;

  public AppCongestionControlContext(
      String appId,
      UserTrafficQuota userTrafficQuota,
      BufferStatusHub workerBufferStatusHub,
      BufferInfo appIdBufferInfo,
      BufferInfo userBufferInfo,
      AbstractSource workerSource,
      UserIdentifier userIdentifier) {
    this.congestionControlFlag = false;
    this.appId = appId;
    this.userIdentifier = userIdentifier;
    this.appBufferInfo = appIdBufferInfo;
    this.userBufferInfo = userBufferInfo;
    this.workerBufferStatusHub = workerBufferStatusHub;
    this.userTrafficQuota = userTrafficQuota;
  }

  public void onCongestionControl() {
    congestionControlFlag = true;
  }

  public void offCongestionControl() {
    congestionControlFlag = false;
  }

  public boolean inCongestionControl() {
    return congestionControlFlag;
  }

  public void updateProduceBytes(long numBytes) {
    long timeNow = System.currentTimeMillis();
    BufferStatusHub.BufferStatusNode node = new BufferStatusHub.BufferStatusNode(numBytes);
    appBufferInfo.updateInfo(timeNow, node);
    userBufferInfo.updateInfo(timeNow, (BufferStatusHub.BufferStatusNode) node.clone());
    workerBufferStatusHub.add(timeNow, (BufferStatusHub.BufferStatusNode) node.clone());
  }

  public BufferInfo getUserBufferInfo() {
    return userBufferInfo;
  }

  public BufferInfo getAppBufferInfo() {
    return appBufferInfo;
  }

  public String getAppId() {
    return appId;
  }

  public UserIdentifier getUserIdentifier() {
    return userIdentifier;
  }

  public UserTrafficQuota getUserTrafficQuota() {
    return userTrafficQuota;
  }

  public void updateUserTrafficQuota(UserTrafficQuota userTrafficQuota) {
    this.userTrafficQuota = userTrafficQuota;
  }
}
