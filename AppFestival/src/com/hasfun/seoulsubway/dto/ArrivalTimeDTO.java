package com.hasfun.seoulsubway.dto;

import java.util.ArrayList;

public class ArrivalTimeDTO {
	public static final String SearchArrivalTimeOfLine2SubwayByIDService = "SearchArrivalTimeOfLine2SubwayByIDService";
	public static final String list_total_count = "list_total_count";
	public static final String row = "row"; 
	public static final String SUBWAYCODE = "SUBWAYCODE";
	public static final String DESTSTATION_CODE = "DESTSTATION_CODE";
	public static final String DESTSTATION_NAME = "DESTSTATION_NAME";
	public static final String TRAINCODE = "TRAINCODE";
	public static final String LEFTTIME = "LEFTTIME";
	public static final String ARRIVETIME = "ARRIVETIME";
	public static final String STATION_CD = "STATION_CD";
	public static final String FR_CODE = "FR_CODE";
	
	private ArrayList<InnerData> innerdata;
	private	String inout;
	private	String station;
	
	
	public ArrivalTimeDTO(){
		innerdata = new ArrayList<ArrivalTimeDTO.InnerData>();
	}
	
	
	
	public ArrayList<InnerData> getInnerdataList() {
		return innerdata;
	}
	
	
	
	public InnerData getInnerData(){
		return new InnerData();
	}
	
	public void setInnerdata(InnerData innerData) {
		innerdata.add(innerData);
	}

	public String getInout() {
		return inout;
	}



	public void setInout(String inout) {
		this.inout = inout;
	}

	public String getStation() {
		return station;
	}



	public void setStation(String station) {
		this.station = station;
	}

	public class InnerData {
		private String frCode;
		private String stationCd;
		private String arriveTime;
		private String leftTIme;
		private String trainCode;
		private String deststationCode;
		private String deststationName;
		private String subwayCode;
		
		public String getFrCode() {
			return frCode;
		}
		public void setFrCode(String frCode) {
			this.frCode = frCode;
		}
		public String getStationCd() {
			return stationCd;
		}
		public void setStationCd(String stationCd) {
			this.stationCd = stationCd;
		}
		public String getArriveTime() {
			return arriveTime;
		}
		public void setArriveTime(String arriveTime) {
			this.arriveTime = arriveTime;
		}
		public String getLeftTIme() {
			return leftTIme;
		}
		public void setLeftTIme(String leftTIme) {
			this.leftTIme = leftTIme;
		}
		public String getTrainCode() {
			return trainCode;
		}
		public void setTrainCode(String trainCode) {
			this.trainCode = trainCode;
		}
		public String getDeststationCode() {
			return deststationCode;
		}
		public void setDeststationCode(String deststationCode) {
			this.deststationCode = deststationCode;
		}
		public String getDeststationName() {
			return deststationName;
		}
		public void setDeststationName(String deststationName) {
			this.deststationName = deststationName;
		}
		public String getSubwayCode() {
			return subwayCode;
		}
		public void setSubwayCode(String subwayCode) {
			this.subwayCode = subwayCode;
		}
	}
	
	
}
