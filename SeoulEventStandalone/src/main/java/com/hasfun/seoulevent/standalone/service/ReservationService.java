package com.hasfun.seoulevent.standalone.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface ReservationService {
	public void getReservation() throws ClientProtocolException, IOException;
}
