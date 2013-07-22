package com.hasfun.seoulevent.standalone.dao;

import java.util.Map;

public interface ReservationDAO {
	public int insertReservation(Map<String, Object> map);
	public int deleteReservation(String gubun);
}
