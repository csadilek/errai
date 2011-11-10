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
import org.jboss.errai.codegen.framework.Statement;
import org.jboss.errai.codegen.framework.builder.BlockBuilder;
import org.jboss.errai.codegen.framework.builder.BuildCallback;
import org.jboss.errai.codegen.framework.builder.CaseBlockBuilder;
import org.jboss.errai.codegen.framework.builder.ContextualSwitchBlockBuilder;
import org.jboss.errai.codegen.framework.builder.StatementEnd;
import org.jboss.errai.codegen.framework.builder.SwitchBlockBuilder;
import org.jboss.errai.codegen.framework.builder.callstack.CallWriter;
import org.jboss.errai.codegen.framework.builder.callstack.DeferredCallElement;
import org.jboss.errai.codegen.framework.builder.callstack.DeferredCallback;
import org.jboss.errai.codegen.framework.control.SwitchBlock;
import org.jboss.errai.codegen.framework.literal.ByteValue;
import org.jboss.errai.codegen.framework.literal.CharValue;
import org.jboss.errai.codegen.framework.literal.IntValue;
import org.jboss.errai.codegen.framework.literal.LiteralFactory;
import org.jboss.errai.codegen.framework.literal.LiteralValue;
import org.jboss.errai.codegen.framework.literal.ShortValue;

/**
 * StatementBuilder to generate switch blocks.
 *
 * @author Christian Sadilek <csadilek@redhat.com>
 */
public class SwitchBlockBuilderImpl extends AbstractStatementBuilder implements SwitchBlockBuilder,
    ContextualSwitchBlockBuilder, CaseBlockBuilder {

  private SwitchBlock switchBlock;

  protected SwitchBlockBuilderImpl(Context context, CallElementBuilder callElementBuilder) {
    super(context, callElementBuilder);
  }

  @Override
  public CaseBlockBuilder switch_() {
    return switch_(new SwitchBlock());
  }

  @Override
  public CaseBlockBuilder switch_(Statement statement) {
    return switch_(new SwitchBlock(statement));
  }

  private CaseBlockBuilder switch_(final SwitchBlock switchBlock) {
    this.switchBlock = switchBlock;
    appendCallElement(new DeferredCallElement(new DeferredCallback() {
      @Override
      public void doDeferred(CallWriter writer, Context context, Statement statement) {
        if (statement != null) {
          switchBlock.setSwitchExpr(statement);
          switchBlock.setSwitchExpr(writer.getCallString());
        }
        writer.reset();
        writer.append(switchBlock.generate(Context.create(context)));
      }
    }));

    return this;
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(IntValue value) {
    switchBlock.addCase(value);
    return caseBlock(value);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(int value) {
    IntValue val = (IntValue) LiteralFactory.getLiteral(value);
    return case_(val);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(CharValue value) {
    switchBlock.addCase(value);
    return caseBlock(value);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(char value) {
    CharValue val = (CharValue) LiteralFactory.getLiteral(value);
    return case_(val);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(ByteValue value) {
    switchBlock.addCase(value);
    return caseBlock(value);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(byte value) {
    ByteValue val = (ByteValue) LiteralFactory.getLiteral(value);
    return case_(val);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(ShortValue value) {
    switchBlock.addCase(value);
    return caseBlock(value);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(short value) {
    ShortValue val = (ShortValue) LiteralFactory.getLiteral(value);
    return case_(val);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(LiteralValue<Enum<?>> value) {
    switchBlock.addCase(value);
    return caseBlock(value);
  }

  @Override
  public BlockBuilder<CaseBlockBuilder> case_(Enum<?> value) {
    LiteralValue<Enum<?>> val = (LiteralValue<Enum<?>>) LiteralFactory.getLiteral(value);
    return case_(val);
  }

  private BlockBuilder<CaseBlockBuilder> caseBlock(LiteralValue<?> value) {
    return new BlockBuilderImpl<CaseBlockBuilder>(switchBlock.getCaseBlock(value),
        new BuildCallback<CaseBlockBuilder>() {

          @Override
          public CaseBlockBuilder callback(Statement statement) {
            return SwitchBlockBuilderImpl.this;
          }

          @Override
          public Context getParentContext() {
            return context;
          }
        });
  }

  @Override
  public BlockBuilder<StatementEnd> default_() {
    return new BlockBuilderImpl<StatementEnd>(switchBlock.getDefaultBlock(),
        new BuildCallback<StatementEnd>() {

          @Override
          public StatementEnd callback(Statement statement) {
            return SwitchBlockBuilderImpl.this;
          }

          @Override
          public Context getParentContext() {
            return context;
          }
        });
  }
}