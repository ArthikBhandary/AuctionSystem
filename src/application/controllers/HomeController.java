package application.controllers;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class HomeController extends NextPageController {
	
	@FXML
	public void adminlogin(ActionEvent event) throws Exception
	{
		//msg.setText("yolo");
		System.out.println("To enter starting bidding prices");
		try
		{
			nextPage(event, "../view/Admin_login.fxml");
		}catch(IOException e)
		{
			System.out.println(e);
		}
		
	}
	
	@FXML
	public void bid(ActionEvent event) throws Exception
	{
		//msg.setText("yolo");
		System.out.println("Bid ");
		System.out.println("To enter starting bidding prices");
		try
		{
			nextPage(event, "../view/CustomerBid.fxml");
		}catch(IOException e)
		{
			System.out.println(e);
		}
		
	}
	
	@FXML
	public void buy(ActionEvent event)
	{
		//msg.setText("yolo");
		System.out.println("Sold");
		System.out.println("Bid ");
		System.out.println("To enter starting bidding prices");
		try
		{
			nextPage(event, "../view/CustomerBuy.fxml");
		} catch(IOException e)
		{
			System.out.println(e);
		}
	}



}
