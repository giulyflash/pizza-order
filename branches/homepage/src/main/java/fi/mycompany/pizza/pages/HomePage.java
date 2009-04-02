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

package fi.mycompany.pizza.pages;
import fi.mycompany.pizza.PizzaApplication;
import fi.mycompany.pizza.components.OrderList;
import fi.mycompany.pizza.model.Order;
import fi.mycompany.pizza.service.PizzaService;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;

/**
 *
 * @author Toni
 */
public final class HomePage extends WebPage {

    protected PizzaService getPizzaService(){
        return PizzaApplication.getPizzaService();
    }

    public HomePage() {
        super ();
        add(new OrderList("openOrderList") {

            @Override
            protected List<Order> getOrders() {
                return getPizzaService().getActiveOrders();
            }
        });
        add(new OrderList("oldOrderList") {

            @Override
            protected List<Order> getOrders() {
                return getPizzaService().getOrderHistory();
            }
        });
        add(new Link("newOrderButton") {

            @Override
            public void onClick() {
                Order order = getPizzaService().openOrder();
                setResponsePage(OrderPage.class, new PageParameters("id="+order.getIdentifier()));

            }
        });

    }
}

