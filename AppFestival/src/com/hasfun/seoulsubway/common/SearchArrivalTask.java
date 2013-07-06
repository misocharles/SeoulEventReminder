package com.hasfun.seoulsubway.common;

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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.hasfun.seoulsubway.R;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;

public class SearchArrivalTask extends AsyncTask<String, Void, String> {
	private final Logger log = Logger.getLogger(this.getClass());
	private String url = "";
	private ArrivalTimeDTO arrivalTimeDTO = null;
	private IPublicApiResult iPublicApiResult = null; 
	@Override
	protected void onPreExecute() {
		log.info("onPreExecute");
	}

	public SearchArrivalTask() {
	}

	public SearchArrivalTask(IPublicApiResult iPublicApiResult) {
		this.url = url;
		this.iPublicApiResult = iPublicApiResult;
	}

	// 1 : url , 2 : data
	@Override
	protected String doInBackground(String... params) {
		String res = "";
		try {
			String serverString = "";
			String url = "http://openapi.seoul.go.kr:8088/4150495f32313130626967656e697573/json/SearchArrivalTimeOfLine2SubwayByIDService/1/1";
			String stationCode = params[0];
			String inout = params[1]; 
			String daytype = params[2];
			String finalUrl = url + "/" + stationCode + "/" + inout + "/" + daytype;
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
			arrivalTimeDTO = new ArrivalTimeDTO();
			ArrayList<ArrivalTimeDTO.InnerData> innerDataList = arrivalTimeDTO.getInnerdataList(); 
			JSONObject rawData = new JSONObject(result);
			JSONObject arrivalData = (JSONObject) rawData.get(ArrivalTimeDTO.SearchArrivalTimeOfLine2SubwayByIDService);
			JSONArray searchData = (JSONArray) arrivalData.get(ArrivalTimeDTO.row);
			for(int i = 0 ; i < searchData.length() ; i++){
				JSONObject data = searchData.getJSONObject(i);
				ArrivalTimeDTO.InnerData innerData = arrivalTimeDTO.getInnerData();
				innerData.setArriveTime(data.getString(ArrivalTimeDTO.ARRIVETIME));
				innerData.setDeststationCode(data.getString(ArrivalTimeDTO.DESTSTATION_CODE));
				innerData.setDeststationName(data.getString(ArrivalTimeDTO.DESTSTATION_NAME));
				innerData.setFrCode(data.getString(ArrivalTimeDTO.FR_CODE));
				innerData.setLeftTIme(data.getString(ArrivalTimeDTO.LEFTTIME));
				innerData.setStationCd(data.getString(ArrivalTimeDTO.STATION_CD));
				innerData.setSubwayCode(data.getString(ArrivalTimeDTO.SUBWAYCODE));
				innerData.setTrainCode(data.getString(ArrivalTimeDTO.TRAINCODE));
				arrivalTimeDTO.setInnerdata(innerData);
			}
			iPublicApiResult.getResult(arrivalTimeDTO);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public Object getResult(){
		return arrivalTimeDTO;
	}
}
