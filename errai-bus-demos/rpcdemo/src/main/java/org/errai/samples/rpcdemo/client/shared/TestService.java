/*
 * Copyright 2010 JBoss, a divison Red Hat, Inc
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

package org.errai.samples.rpcdemo.client.shared;

import java.util.Date;
import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface TestService {
  public long getMemoryFree();

  public String append(String str, String str2);

  public void update(String status);

  public long add(long x, long y);

  List<Date> getDates();

  Date getDate();

  public void exception() throws TestException;

}
