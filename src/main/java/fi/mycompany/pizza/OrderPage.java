package fi.mycompany.pizza;

import fi.mycompany.pizza.model.Order;
import fi.mycompany.pizza.model.OrderRow;
import java.util.LinkedList;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
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

	// TODO Add any page properties or variables here

    protected Order getOrder(){
        return PizzaApplication.getOrder();
    }

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public OrderPage(final PageParameters parameters) {
        //final PropertyModel<List<OrderRow>> model = new PropertyModel<List<OrderRow>>(PizzaApplication.getOrder(), "rows");
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
                synchronized(PizzaApplication.getOrder()){
                    for (OrderRow orderRow : getOrder().getRows()) {
                        total += orderRow.getPrice();
                    }
                }
                return total;
            }
        }));
        tableContainer.add(new AbstractAjaxTimerBehavior(Duration.seconds(2)) {

            @Override
            protected void onTimer(AjaxRequestTarget target) {
                target.addComponent(tableContainer);

            }
        });
        add(tableContainer);
        add(new OrderRowForm("newRow"));
        add(new Chat("chat") {

            @Override
            protected LinkedList<Message> getMessageList() {
                return PizzaApplication.getMessageList();
            }
        });
        // TODO Add your page's components here
    }
    

    public static class OrderRowForm extends StatelessForm<OrderRow> {

        public OrderRowForm(String id) {
            super(id);
            OrderRow row = new OrderRow();
            setModel(new CompoundPropertyModel<OrderRow>(row));
            add(new RequiredTextField("orderer"));
            add(new RequiredTextField("content"));
            add(new RequiredTextField("price"));
            
        }
        @Override
        protected void onSubmit() {
            OrderRow row = getModelObject();
            synchronized(PizzaApplication.getOrder()){
                PizzaApplication.getOrder().getRows().add(row);
            }
            
            setModel(new CompoundPropertyModel<OrderRow>(new OrderRow()));
            setResponsePage(OrderPage.class);
        }



    }
}
