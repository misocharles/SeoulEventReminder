package com.hasfun.seoulevent.standalone;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hasfun.seoulevent.standalone.service.ReservationService;


public class Main {
	private static Log log = LogFactory.getLog(Main.class);
	
	public static ApplicationContext context;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Main");
		String[] configLocations = new String[] { "./config/applicationContext-standalone.xml" };
		context = new ClassPathXmlApplicationContext(configLocations);
		ReservationService reservationService = context.getBean("ReservationService", ReservationService.class);;
		try {
			reservationService.getCulture();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
