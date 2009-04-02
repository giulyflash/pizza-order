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

package fi.mycompany.pizza.service.impl;

import fi.mycompany.pizza.components.OrderList;
import fi.mycompany.pizza.model.Order;
import fi.mycompany.pizza.service.PizzaService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Toni
 */
public class PizzaServiceImpl implements PizzaService{

    private Vector<Order> orders;

    public PizzaServiceImpl() {
        orders = new Vector<Order>();
    }



    public List<Order> getActiveOrders() {
        List<Order> orderList = new ArrayList<Order>();
        for (Order order : orders) {
            if(order.isActive()){
                orderList.add(order);
            }
        }
        return orderList;
    }

    public List<Order> getOrderHistory() {
        List<Order> orderList = new ArrayList<Order>();
        for (Order order : orders) {
            if(!order.isActive()){
                orderList.add(order);
            }
        }
        return orderList;
    }

    public Order openOrder() {
        Order order = new Order();
        order.setCreated(new Date());
        int identifier = orders.size();
        order.setIdentifier(identifier);
        order.setActive(true);
        orders.add(order);
        return order;
    }

    public Order getOrder(int identifier){
        return orders.get(identifier);
    }

    public void closeOrder(Order order){
        order.setActive(false);
    }


}
