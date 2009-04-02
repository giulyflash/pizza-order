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
package fi.mycompany.pizza.components.chat;

import java.util.LinkedList;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

/**
 *
 * @author Toni
 */
public abstract class Chat extends Panel {

    private int maxMessages = 10;

    public Chat(String id) {
        super(id);
        WebMarkupContainer chatContainer = new WebMarkupContainer("chatContainer");
        IModel model = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return getMessageList();
            }
        };
        ListView<Message> messageView = new ListView<Message>("messages", model) {

            @Override
            protected void populateItem(ListItem<Message> item) {
                Message message = item.getModelObject();
                item.add(new Label("username", new PropertyModel<Message>(message, "username")));
                item.add(new Label("message", new PropertyModel<Message>(message, "message")));
            }
        };
        chatContainer.add(messageView);
        chatContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
        chatContainer.setOutputMarkupId(true);
        add(chatContainer);
        add(new NewMessageForm("newMessageForm", chatContainer).setOutputMarkupId(true));
    }

    protected abstract LinkedList<Message> getMessageList();

    protected void onMessageListChange() {
    }

    /**
     * @param maxMessages the maxMessages to set
     */
    public void setMaxMessages(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    class NewMessageForm extends Form<Message> {

        public NewMessageForm(String id, final Component container) {
            super(id);
            setModel(new CompoundPropertyModel(new Message()));

            AjaxButton submitButton = new AjaxButton("submit", this) {

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    target.addComponent(container);
                    target.addComponent(NewMessageForm.this);
                    getMessageList().addLast(NewMessageForm.this.getModelObject());
                    while (getMessageList().size() > maxMessages) {
                        getMessageList().removeFirst();
                    }
                    Message message = new Message();
                    message.setUsername(NewMessageForm.this.getModelObject().getUsername());
                    NewMessageForm.this.setModel(new CompoundPropertyModel(message));
                }
            };
            setDefaultButton(submitButton);
            add(new TextField("username"));
            add(new TextField("message"));
            add(submitButton);
        }

        @Override
        protected void onSubmit() {
        }
    }
}
