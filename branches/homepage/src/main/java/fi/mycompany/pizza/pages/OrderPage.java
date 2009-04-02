package fi.mycompany.pizza.pages;

import fi.mycompany.pizza.components.chat.Message;
import fi.mycompany.pizza.components.chat.Chat;
import fi.mycompany.pizza.*;
import fi.mycompany.pizza.model.Order;
import fi.mycompany.pizza.model.OrderRow;
import fi.mycompany.pizza.service.PizzaService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

/**
 * Homepage
 */
public class OrderPage extends WebPage {

	private static final long serialVersionUID = 1L;
    private int orderId;


	// TODO Add any page properties or variables here
    protected PizzaService getPizzaService(){
        return PizzaApplication.getPizzaService();
    }

    protected Order getOrder(){
        if(orderId == -1){
            return new Order();
        }
        return getPizzaService().getOrder(orderId);
    }

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public OrderPage(final PageParameters parameters) {
        //final PropertyModel<List<OrderRow>> model = new PropertyModel<List<OrderRow>>(PizzaApplication.getOrder(), "rows");
        orderId = parameters.getAsInteger("id",-1);
        final LoadableDetachableModel model = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return getOrder().getRows();
            }
        };
        final WebMarkupContainer tableContainer = new WebMarkupContainer("container");
        final ListView<OrderRow> rowView = new ListView<OrderRow>("rows", model) {

            @Override
            protected void populateItem(ListItem<OrderRow> item) {
                final OrderRow orderRow = item.getModelObject();
                item.add(new Label("orderer", new PropertyModel<OrderRow>(orderRow, "orderer")));
                item.add(new Label("content", new PropertyModel<OrderRow>(orderRow, "content")));
                item.add(new Label("price", new PropertyModel<OrderRow>(orderRow, "price")));
                item.add(new AjaxFallbackLink("removeLink") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        target.addComponent(tableContainer);
                        synchronized(getOrder()){
                            getOrder().getRows().remove(orderRow);
                        }
                        
                    }
                });
            }

        };
        tableContainer.setOutputMarkupId(true);
        tableContainer.add(rowView);
        tableContainer.add(new Label("total", new AbstractReadOnlyModel<Double>() {

            @Override
            public Double getObject() {
                double total = 0;
                synchronized(getOrder()){
                    for (OrderRow orderRow : getOrder().getRows()) {
                        total += orderRow.getPrice();
                    }
                }
                return total;
            }
        }));
        tableContainer.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));

        add(tableContainer);
        add(new OrderRowForm("newRow", Collections.singletonList(tableContainer)));
        add(new Chat("chat") {

            @Override
            protected LinkedList<Message> getMessageList() {
                return PizzaApplication.getMessageList();
            }
        });
        add(new BookmarkablePageLink("backLink", HomePage.class));
        add(new Link("closeButton") {

            @Override
            public void onClick() {
                getOrder().setActive(false);
                setResponsePage(OrderPage.class, new PageParameters("id="+getOrder().getIdentifier()));
            }
        });
    }

    

    public class OrderRowForm extends StatelessForm<OrderRow> {

        public OrderRowForm(String id, final List<? extends Component> updateComponents) {
            super(id);
            setOutputMarkupId(true);
            OrderRow row = new OrderRow();
            setModel(new CompoundPropertyModel<OrderRow>(row));
            add(new RequiredTextField("orderer"));
            add(new RequiredTextField("content"));
            add(new RequiredTextField("price"));
            add(new AjaxFallbackButton("submitButton", this) {

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    OrderRow row = OrderRowForm.this.getModelObject();
                    synchronized(getOrder()){
                       getOrder().getRows().add(row);
                    }
                    OrderRowForm.this.setModel(new CompoundPropertyModel<OrderRow>(new OrderRow()));
                    if(target != null){
                        for (Component component : updateComponents) {
                            target.addComponent(component);
                            target.addComponent(OrderRowForm.this);
                        }
                    }else{
                        setResponsePage(OrderPage.class, new PageParameters("id="+getOrder().getIdentifier()));
                    }
                }
            });
        }
        @Override
        protected void onSubmit() {
        }



    }
}
