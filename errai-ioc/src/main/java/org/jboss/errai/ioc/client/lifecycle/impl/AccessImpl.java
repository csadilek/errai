/*
 * Copyright 2015 JBoss, by Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.ioc.client.lifecycle.impl;

import javax.enterprise.context.Dependent;

import org.jboss.errai.ioc.client.lifecycle.api.Access;

@Dependent
public class AccessImpl<T> extends LifecycleEventImpl<T> implements Access<T> {
  
  private boolean isMethodAccess;
  private boolean isFieldAccess;
  private String accessedName;

  public AccessImpl(final boolean isMethodAccess, final boolean isFieldAccess, final String accessedName) {
    this.isMethodAccess = isMethodAccess;
    this.isFieldAccess = isFieldAccess;
    this.accessedName = accessedName;
  }
  
  public AccessImpl() {}

  @Override
  public boolean isMethodAccess() {
    return isMethodAccess;
  }
  
  @Override
  public void setIsMethodAccess(final boolean isMethodAccess) {
    this.isMethodAccess = isMethodAccess;
  }

  @Override
  public String getMethodOrFieldName() {
    return accessedName;
  }
  
  @Override
  public void setMethodOrFieldName(final String methodOrFieldName) {
    this.accessedName = methodOrFieldName;
  }

  @Override
  public Class<?> getEventType() {
    return Access.class;
  }

  @Override
  public boolean isFieldAccess() {
    return isFieldAccess;
  }

  @Override
  public void setIsFieldAccess(final boolean isFieldAccessed) {
    this.isFieldAccess = isFieldAccessed;
  }

}
