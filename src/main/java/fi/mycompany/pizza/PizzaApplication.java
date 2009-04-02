package fi.mycompany.pizza;

import fi.mycompany.pizza.components.chat.Message;
import fi.mycompany.pizza.pages.OrderPage;
import fi.mycompany.pizza.model.Order;
import fi.mycompany.pizza.model.OrderRow;
import fi.mycompany.pizza.pages.HomePage;
import fi.mycompany.pizza.service.PizzaService;
import fi.mycompany.pizza.service.impl.PizzaServiceImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.mycompany.PizzaApp.Start#main(String[])
 */
public class PizzaApplication extends WebApplication
{

    private static Order order;
    private static LinkedList<Message> messageList;
    private static PizzaService pizzaService;

    public static Order getOrder(){
        if(order == null){
            order = new Order();
        }
        return order;
    }

    public static LinkedList<Message> getMessageList(){
        if(messageList == null){
            messageList = new LinkedList<Message>();
        }
        return messageList;
    }

    /**
     * @return the pizzaService
     */
    public static PizzaService getPizzaService() {
        return pizzaService;
    }

    public void clearOrder(){
        getOrder().setRows(new ArrayList<OrderRow>());
    }
    /**
     * Constructor
     */
	public PizzaApplication()
	{
        pizzaService = new PizzaServiceImpl();
	}

    public static PizzaApplication get(){
        return get();
    }
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

    @Override
    public Session newSession(Request request, Response response) {
        return new PizzaSession(request);
    }

    @Override
    protected void init() {
        mountBookmarkablePage("tilaus", OrderPage.class);
        mountBookmarkablePage("index", HomePage.class);
    }




}
