package com.hasfun.seoulevent.dao;

import java.util.List;
import java.util.Map;

import com.hasfun.seoulevent.dto.EventDTO;

public interface RestDao {
	public List<EventDTO> selectEvent(Map<String, Object> reqMap);
}
