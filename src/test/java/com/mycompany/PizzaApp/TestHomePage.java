package com.mycompany.PizzaApp;

import fi.mycompany.pizza.OrderPage;
import fi.mycompany.pizza.PizzaApplication;
import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new PizzaApplication());
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(OrderPage.class);

		//assert rendered page class
		tester.assertRenderedPage(OrderPage.class);

		//assert rendered label component
//		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
