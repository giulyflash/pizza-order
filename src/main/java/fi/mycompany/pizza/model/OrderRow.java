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

package fi.mycompany.pizza.model;

import java.io.Serializable;

/**
 *
 * @author Toni
 */
public class OrderRow implements Serializable{
    private String orderer;
    private String content;
    private double price;

    /**
     * @return the orderer
     */
    public String getOrderer() {
        return orderer;
    }

    /**
     * @param orderer the orderer to set
     */
    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
