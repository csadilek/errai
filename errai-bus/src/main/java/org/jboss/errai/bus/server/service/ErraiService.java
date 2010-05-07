/*
 * Copyright 2009 JBoss, a divison Red Hat, Inc
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

package org.jboss.errai.bus.server.service;

import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.framework.RequestDispatcher;
import org.jboss.errai.bus.server.api.ServerMessageBus;
import org.jboss.errai.bus.server.api.SessionProvider;

/**
 * The <tt>ErraiService</tt> is a minimal template for what is needed. It facilitates the ability to store a message,
 * obtain the server bus and configuration
 */
public interface ErraiService extends ServiceComposite{
  public static final String AUTHORIZATION_SVC_SUBJECT = "AuthenticationService";
  public static final String SESSION_AUTH_DATA = "ErraiAuthData";

  /**
   * Stores the specified message
   *
   * @param message - the message to store
   */
  public void store(Message message);

  /**
   * Retrieves the server message bus employed by this service
   *
   * @return the server message bus
   */
  public ServerMessageBus getBus();


  /**
   * Gets the configuration used to initalize the service
   *
   * @return the errai service configurator
   */
  public ErraiServiceConfigurator getConfiguration();  
}
