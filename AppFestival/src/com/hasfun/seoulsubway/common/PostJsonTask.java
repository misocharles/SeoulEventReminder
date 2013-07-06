package com.hasfun.seoulsubway.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;


public class PostJsonTask extends AsyncTask<String, Void, String> {
	private final Logger log = Logger.getLogger(this.getClass());
	private String type = "";
	private String jsonString;

	@Override
	protected void onPreExecute() {
		log.info("onPreExecute");
	}

	public PostJsonTask() {
	}

	public PostJsonTask(String type) {
		this.type = type;
	}

	// 1 : url , 2 : data
	@Override
	protected String doInBackground(String... params) {
		String res = "";
		try {
			// The URL for making the POST request
			String url = "sample";
			url = url + params[0];
			log.debug("url : " + url);
			this.jsonString = params[1];
			HttpHeaders requestHeaders = new HttpHeaders();

			ArrayList<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			requestHeaders.set("accept-charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
			requestHeaders.set("accept-language", "ko-KR, en-US");
			requestHeaders.set("accept-encoding", "gzip,deflate");
			
			// Populate the Message object to serialize and headers in an
			// HttpEntity object to use for the request
			HttpEntity<String> requestEntity = new HttpEntity<String>(new String(params[1].getBytes(), "utf-8"), requestHeaders);
			log.debug("langth " + new String(params[1].getBytes(), "utf-8").length());
			log.debug("json : " + params[1]);
			// Create a new RestTemplate instance
			RestTemplate restTemplate = new MyRestTemplate();
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

			// Make the network request, posting the message, expecting a
			// String in response from the server
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			log.info("?�상 처리 ?�료");
			res = response.getBody();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return res;
	}

	@Override
	protected void onPostExecute(String result) {
		/*if (this.type.equals("login") && result != null) {
			ObjectMapper om = new ObjectMapper();
			Map<String, String> map = null;
			try {
				map = om.readValue(result, Map.class);
				// ?�음 처리 
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}*/
	}
}
