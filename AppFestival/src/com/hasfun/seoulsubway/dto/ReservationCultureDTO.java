package com.hasfun.seoulsubway.dto;

import java.util.ArrayList;

public class ReservationCultureDTO {
	public static final String ListPublicReservationCulture = "ListPublicReservationCulture";
	public static final String list_total_count = "list_total_count";
	public static final String row = "row"; 
	public static final String GUBUN = "GUBUN";
	public static final String SVCID = "SVCID";
	public static final String MAXCLASSNM = "MAXCLASSNM";
	public static final String MINCLASSNM = "MINCLASSNM";
	public static final String SVCSTATNM = "SVCSTATNM";
	public static final String SVCNM = "SVCNM";
	public static final String PAYATNM = "PAYATNM";
	public static final String PLACENM = "PLACENM";
	public static final String USETGTINFO = "USETGTINFO";
	
	private ArrayList<InnerData> innerdata;
	
	
	public ReservationCultureDTO(){
		innerdata = new ArrayList<ReservationCultureDTO.InnerData>();
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

	public class InnerData {
		private String gubun;
		private String svcid;
		private String maxclassnm;
		private String minclassnm;
		private String svcstatnm;
		private String svcnm;
		private String payatnm;
		private String placenm;
		private String usetgtinfo;
		
		public String getGubun() {
			return gubun;
		}
		public void setGubun(String gubun) {
			this.gubun = gubun;
		}
		public String getSvcid() {
			return svcid;
		}
		public void setSvcid(String svcid) {
			this.svcid = svcid;
		}
		public String getMaxclassnm() {
			return maxclassnm;
		}
		public void setMaxclassnm(String maxclassnm) {
			this.maxclassnm = maxclassnm;
		}
		public String getMinclassnm() {
			return minclassnm;
		}
		public void setMinclassnm(String minclassnm) {
			this.minclassnm = minclassnm;
		}
		public String getSvcstatnm() {
			return svcstatnm;
		}
		public void setSvcstatnm(String svcstatnm) {
			this.svcstatnm = svcstatnm;
		}
		public String getSvcnm() {
			return svcnm;
		}
		public void setSvcnm(String svcnm) {
			this.svcnm = svcnm;
		}
		public String getPayatnm() {
			return payatnm;
		}
		public void setPayatnm(String payatnm) {
			this.payatnm = payatnm;
		}
		public String getPlacenm() {
			return placenm;
		}
		public void setPlacenm(String placenm) {
			this.placenm = placenm;
		}
		public String getUsetgtinfo() {
			return usetgtinfo;
		}
		public void setUsetgtinfo(String usetgtinfo) {
			this.usetgtinfo = usetgtinfo;
		}
	}
	
	
}
