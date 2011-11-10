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

package org.jboss.errai.codegen.framework.builder;

import org.jboss.errai.codegen.framework.BooleanExpression;
import org.jboss.errai.codegen.framework.BooleanOperator;
import org.jboss.errai.codegen.framework.Statement;

/**
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public interface ContextualLoopBuilder extends Statement, Builder {
  public BlockBuilder<StatementEnd> foreach(String loopVarName);
  public BlockBuilder<StatementEnd> foreach(String loopVarName, Class<?> loopVarType);

  public BlockBuilder<StatementEnd> for_(Statement initializer, BooleanExpression condition);
  public BlockBuilder<StatementEnd> for_(Statement initializer, BooleanExpression condition, Statement countingExpression);

  public BlockBuilder<WhileBuilder> do_();

  public BlockBuilder<StatementEnd> while_();
  public BlockBuilder<StatementEnd> while_(BooleanOperator op, Statement rhs);
  public BlockBuilder<StatementEnd> while_(BooleanOperator op, Object rhs);
}
