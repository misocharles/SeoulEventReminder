package com.hasfun.seoulevent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hasfun.seoulevent.service.RestService;


@Controller
public class RestController{
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private RestService restService;
	
	/**
	 * app version check
	 * @param request
	 * @param response
	 * @param reqMap
	 * @param resMap
	 * @return
	 */
	@RequestMapping(value="/api", method=RequestMethod.GET,  headers="Accept=application/xml, application/json")
	public @ResponseBody Map<String, Object> api(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("dis") String dis){
		HashMap<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("lat", lat);
		reqMap.put("lon", lon);
		reqMap.put("dis", dis);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("data", restService.selectEvent(reqMap));
		return resMap;
	}
}
