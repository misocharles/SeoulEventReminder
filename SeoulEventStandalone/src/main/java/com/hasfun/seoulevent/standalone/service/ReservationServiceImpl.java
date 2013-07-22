package com.hasfun.seoulevent.standalone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.hasfun.seoulevent.standalone.base.Constants;
import com.hasfun.seoulevent.standalone.dao.ReservationDAO;

public class ReservationServiceImpl implements ReservationService {

	private static Log log = LogFactory.getLog(ReservationServiceImpl.class);
	private final String GUBUN = "GUBUN";
	private final String SVCID = "SVCID";
	private final String MAXCLASSNM = "MAXCLASSNM";
	private final String MINCLASSNM = "MINCLASSNM";
	private final String SVCSTATNM = "SVCSTATNM";
	private final String SVCNM = "SVCNM";
	private final String PAYATNM = "PAYATNM";
	private final String PLACENM = "PLACENM";
	private final String USETGTINFO = "USETGTINFO";
	private final String SVCURL = "SVCURL";
	private final String[] type = { "ListPublicReservationCulture", "ListPublicReservationEducation", "ListPublicReservationInstitution", "ListPublicReservationSport" };
	private final String[] gubun = { "A", "B", "C", "D" };
	@Autowired
	ReservationDAO reservationDAO;

	@Override
	public void getReservation() throws ClientProtocolException, IOException {
		String serverString = "";
		for (int typeidx = 0; typeidx < type.length; typeidx++) {
			reservationDAO.deleteReservation(gubun[typeidx]);
			String url = "http://openapi.seoul.go.kr:8088/" + Constants.CultureApiKey + "/json/" + type[typeidx] + "/1/10000/";
			// Create Request to server and get response
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			serverString = client.execute(httpget, responseHandler);
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> resMap = null;
			resMap = om.readValue(serverString, Map.class);
			resMap = (Map<String, Object>) resMap.get(type[typeidx]);
			log.debug(type[typeidx] + " list_total_count : " + resMap.get("list_total_count"));
			ArrayList<Map> array = (ArrayList<Map>) resMap.get("row");
			for (int i = 0; i < array.size(); i++) {

				Map map = array.get(i);
				try {
					// 위치 정보로 부터 좌표값 받아 오기(naver);
					String query = (String) map.get(PLACENM);
					if (query.length() > 0) {
						try {
							query = query.replace(">", "");
							query = query.replace("<", "");
							map.put("LAT", convertCoordinate(naverQuery(query)).get("y"));
							map.put("LON", convertCoordinate(naverQuery(query)).get("x"));
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					}
					map.remove(GUBUN);
					map.put(GUBUN, gubun[typeidx]);
					reservationDAO.insertReservation(map);
				} catch (Exception e) {
					log.error("gubun " + type[typeidx] + " error svcid : " + map.get(SVCID));
					log.error(e.getLocalizedMessage());
				}
			}
		}
	};

	private Map<String, String> naverQuery(String query) throws Exception {
		String naverApiUrl = "http://openapi.naver.com/search?key=" + Constants.naverApiKey + "&query=" + query + "&target=local&start=1&display=1";
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(naverApiUrl);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String naverString = client.execute(httpget, responseHandler);
		Document doc = Jsoup.parse(naverString);
		Elements mapx = doc.select("mapx");
		Elements mapy = doc.select("mapy");
		Map map = new HashMap<String, String>();
		map.put("mapx", mapx.text());
		map.put("mapy", mapy.text());
		return map;
	}

	private Map convertCoordinate(Map coord) throws Exception {
		String daumApiUrl = "http://apis.daum.net/local/geo/transcoord?apikey=" + Constants.daumApiKey + "&x=" + coord.get("mapx") + "&y=" + coord.get("mapy")
				+ "&fromCoord=KTM&toCoord=WGS84&output=json";
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(daumApiUrl);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String daumString = client.execute(httpget, responseHandler);
		ObjectMapper om = new ObjectMapper();
		return om.readValue(daumString, Map.class);
	}
}
