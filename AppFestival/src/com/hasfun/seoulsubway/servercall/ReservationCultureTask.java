package com.hasfun.seoulsubway.servercall;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.hasfun.seoulsubway.common.Constants;
import com.hasfun.seoulsubway.common.IPublicApiResult;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;
import com.hasfun.seoulsubway.dto.ReservationCultureDTO;

public class ReservationCultureTask extends AsyncTask<String, Void, String> {
	private final Logger log = Logger.getLogger(this.getClass());
	private String url = "";
	private ReservationCultureDTO reservationCultureDTO = null;
	private IPublicApiResult iPublicApiResult = null; 
	private String stationCode = null;
	@Override
	protected void onPreExecute() {
		log.info("onPreExecute");
	}

	public ReservationCultureTask() {
	}

	public ReservationCultureTask(IPublicApiResult iPublicApiResult) {
		this.url = url;
		this.iPublicApiResult = iPublicApiResult;
	}

	// 1 : url , 2 : data
	@Override
	protected String doInBackground(String... params) {
		String res = "";
		try {
			String serverString = "";
			String url = "http://openapi.seoul.go.kr:8088/"+Constants.PublicApiKey+"/json/ListPublicReservationCulture/1/100/";
			String finalUrl = url ;
			// Create Request to server and get response
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(finalUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			serverString = client.execute(httpget, responseHandler);
			log.info(serverString);
			return serverString;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return res;
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			reservationCultureDTO = new ReservationCultureDTO();
			ArrayList<ReservationCultureDTO.InnerData> innerDataList = reservationCultureDTO.getInnerdataList(); 
			JSONObject rawData = new JSONObject(result);
			JSONObject listData = (JSONObject) rawData.get(ReservationCultureDTO.ListPublicReservationCulture);
			JSONArray searchData = (JSONArray) listData.get(ReservationCultureDTO.row);
			for(int i = 0 ; i < searchData.length() ; i++){
				JSONObject data = searchData.getJSONObject(i);
				ReservationCultureDTO.InnerData innerData = reservationCultureDTO.getInnerData();
				innerData.setGubun(data.getString(ReservationCultureDTO.GUBUN));
				innerData.setMaxclassnm(data.getString(ReservationCultureDTO.MAXCLASSNM));
				innerData.setMinclassnm(data.getString(ReservationCultureDTO.MINCLASSNM));
				innerData.setSvcid(data.getString(ReservationCultureDTO.SVCID));
				innerData.setSvcnm(data.getString(ReservationCultureDTO.SVCNM));
				innerData.setSvcstatnm(data.getString(ReservationCultureDTO.SVCSTATNM));
				innerData.setPayatnm(data.getString(ReservationCultureDTO.PAYATNM));
				innerData.setPlacenm(data.getString(ReservationCultureDTO.PLACENM));
				innerData.setUsetgtinfo(data.getString(ReservationCultureDTO.USETGTINFO));
				reservationCultureDTO.setInnerdata(innerData);
			}
			iPublicApiResult.getResult(reservationCultureDTO);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public Object getResult(){
		return reservationCultureDTO;
	}
}
