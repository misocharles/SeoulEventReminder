package com.hasfun.seoulevent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hasfun.seoulevent.dao.RestDao;
import com.hasfun.seoulevent.dto.EventDTO;

public class RestServiceImpl implements RestService {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private RestDao restDao;

	@Override
	public Map<String, Object> selectEvent(Map<String, Object> reqMap) {
		double lat = Double.parseDouble((String) reqMap.get("lat"));
		double lon = Double.parseDouble((String) reqMap.get("lon"));
		double dis = Double.parseDouble((String) reqMap.get("dis"));
		List<EventDTO> eventList = restDao.selectEvent(null);
		List<EventDTO> outputList = new ArrayList<EventDTO>();
		for(int i = 0; i < eventList.size() ; i++){
			EventDTO eventDto = eventList.get(i);
			double calDis = getDistance_arc(lat, lon, Double.parseDouble(eventDto.getLat()), Double.parseDouble(eventDto.getLon()));
			if(dis > calDis) outputList.add(eventDto);
		}
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("list_total_count", outputList.size());
		data.put("row", outputList);
		return data;
	}

	private static double getDistance_arc(double sLat, double sLong, double dLat, double dLong){  
        final int radius=6371009;

        double uLat=Math.toRadians(sLat-dLat);
        double uLong=Math.toRadians(sLong-dLong);
        double a = Math.sin(uLat/2) * Math.sin(uLat/2) + Math.cos(Math.toRadians(sLong)) * Math.cos(Math.toRadians(dLong)) * Math.sin(uLong/2) * Math.sin(uLong/2);  
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));  
        double distance = radius * c;
        return distance;
    }
}
