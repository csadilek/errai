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

import org.jboss.errai.codegen.framework.Context;
import org.jboss.errai.codegen.framework.Expression;
import org.jboss.errai.codegen.framework.Operator;
import org.jboss.errai.codegen.framework.Statement;
import org.jboss.errai.codegen.framework.literal.LiteralFactory;
import org.jboss.errai.codegen.framework.util.GenUtil;

/**
 * @author Mike Brock <cbrock@redhat.com>
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public abstract class ExpressionBuilder<T extends Operator> implements Statement, Expression<T> {
  protected Statement lhs;
  protected String lhsExpr;

  protected Statement rhs;
  protected String rhsExpr;

  protected T operator;
  protected String operExpr;

  public ExpressionBuilder() {}

  public ExpressionBuilder(Statement rhs, T operator) {
    this.rhs = rhs;
    this.operator = operator;
  }

  public ExpressionBuilder(Statement lhs, Statement rhs, T operator) {
    this(rhs, operator);
    this.lhs = lhs;
  }

  public ExpressionBuilder(String lhsExpr, Statement rhs, T operator) {
    this(rhs, operator);
    this.lhsExpr = lhsExpr;
  }

  public ExpressionBuilder(Object lhs, Object rhs, T operator) {
    this.lhs = (!(lhs instanceof Statement)) ? LiteralFactory.getLiteral(lhs) : (Statement) lhs;
    this.rhs = (!(rhs instanceof Statement)) ? LiteralFactory.getLiteral(rhs) : (Statement) rhs;
    this.operator = operator;
  }

  @Override
  public String generate(Context context) {
    if (operator != null) {
      if (lhs != null)
        operator.assertCanBeApplied(GenUtil.generate(context, lhs).getType());
      if (rhs != null)
        operator.assertCanBeApplied(GenUtil.generate(context, rhs).getType());
    }

    operExpr = "";
    rhsExpr = "";

    if (lhsExpr == null && lhs != null) {
      if (lhs instanceof ExpressionBuilder && this.operator != null) {
        lhsExpr = "(" + lhs.generate(context) + ")";
      }
      else {
        lhsExpr = lhs.generate(context);
      }
    }

    if (this.operator != null) {
      operExpr = " " + this.operator.getCanonicalString() + " ";
    }

    if (rhs != null) {
      if (rhs instanceof ExpressionBuilder) {
        rhsExpr = "(" + rhs.generate(context) + ")";
      }
      else {
        rhsExpr = rhs.generate(context);
      }
    }

    return lhsExpr + operExpr + rhsExpr;
  }

  @Override
  public Statement getLhs() {
    return lhs;
  }

  @Override
  public void setLhs(Statement lhs) {
    this.lhs = lhs;
  }

  @Override
  public String getLhsExpr() {
    return lhsExpr;
  }

  @Override
  public void setLhsExpr(String lhsExpr) {
    this.lhsExpr = lhsExpr;
  }

  @Override
  public Statement getRhs() {
    return rhs;
  }

  @Override
  public void setRhs(Statement rhs) {
    this.rhs = rhs;
  }

  @Override
  public T getOperator() {
    return operator;
  }

  @Override
  public void setOperator(T operator) {
    this.operator = operator;
  }
}