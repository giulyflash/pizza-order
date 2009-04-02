/*
 *  Copyright 2009 Toni.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package fi.mycompany.pizza.components;
import fi.mycompany.pizza.model.Order;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author Toni
 */
public abstract class OrderList extends Panel {
    public OrderList(String id) {
        super (id);
        add(new ListView<Order>("orderList", getOrders()) {

            @Override
            protected void populateItem(ListItem<Order> item) {
                Order order = item.getModelObject();
                item.add(new Label("created", new PropertyModel(order, "created")));
            }
        });
    }

    protected abstract List<Order> getOrders();
}
