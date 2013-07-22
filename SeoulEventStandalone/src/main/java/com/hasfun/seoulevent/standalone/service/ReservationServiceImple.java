package com.hasfun.seoulevent.standalone.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.hasfun.seoulevent.standalone.base.Constants;

public class ReservationServiceImple implements ReservationService{
	
	private static Log log = LogFactory.getLog(ReservationServiceImple.class);
	
	@Override
	public void getCulture() throws ClientProtocolException, IOException{
		String serverString = "";
		String url = "http://openapi.seoul.go.kr:8088/"+Constants.CultureApiKey+"/json/ListPublicReservationCulture/1/1000/";
		// Create Request to server and get response
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		serverString = client.execute(httpget, responseHandler);
		ObjectMapper om = new ObjectMapper();
		Map<String, String> resMap = null;
		resMap = om.readValue(serverString, Map.class);
		String rowData = resMap.get("ListPublicReservationCulture");
		resMap = om.readValue(rowData, Map.class);
		System.out.println(resMap.get("list_total_count"));
		rowData = resMap.get("row"); 
		System.out.println(rowData);
	};
}
