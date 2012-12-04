/*
 * Copyright 2011 JBoss, by Red Hat, Inc
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

package org.jboss.errai.demo.grocery.client.local;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.IOCBeanManager;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ListWidget<M, H extends HasModel<M>> extends VerticalPanel {
  
  @Inject private IOCBeanManager bm;

  protected void setItems(List<M> modelList) {
    // clean up the old widgets before we add new ones
    // (this will eventually become a feature of the ErraiUI framework: ERRAI-375)
    Iterator<Widget> it = iterator();
    while (it.hasNext()) {
      bm.destroyBean(it.next());
      it.remove();
    }

    if (modelList == null) return;

    IOCBeanDef<H> itemBeanDef = bm.lookupBean(getWidgetType());
    for (M model : modelList) {
      H widget = itemBeanDef.newInstance();
      widget.setModel(model);
      add((Widget) widget);
    }
  }
  
  public abstract Class<H> getWidgetType();
}
