/*
 * Copyright 2011 JBoss, a divison Red Hat, Inc
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

package org.jboss.errai.codegen.framework.builder.impl;

import org.jboss.errai.codegen.framework.DefModifiers;
import org.jboss.errai.codegen.framework.Modifier;
import org.jboss.errai.codegen.framework.ThrowsDeclaration;
import org.jboss.errai.codegen.framework.builder.Finishable;
import org.jboss.errai.codegen.framework.builder.MethodBuildCallback;
import org.jboss.errai.codegen.framework.meta.MetaClass;

/**
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class MethodBuilderAbstractOption<T> implements Finishable<T> {
  protected ThrowsDeclaration throwsDeclaration = ThrowsDeclaration.none();
  protected MethodBuildCallback<T> callback;

  public MethodBuilderAbstractOption(MethodBuildCallback<T> callback) {
    this.callback = callback;
  }

  public T throws_(Class<? extends Throwable>... exceptionTypes) {
    throwsDeclaration = ThrowsDeclaration.of(exceptionTypes);
    return callback.callback(null, null, new DefModifiers(Modifier.Abstract), throwsDeclaration);
  }

  public T throws_(MetaClass... exceptions) {
    throwsDeclaration = ThrowsDeclaration.of(exceptions);
    return callback.callback(null, null, new DefModifiers(Modifier.Abstract), throwsDeclaration);
  }

  @Override
  public T finish() {
    if (callback != null) {
      return callback.callback(null, null, new DefModifiers(Modifier.Abstract), throwsDeclaration);
    }
    return null;
  }
}