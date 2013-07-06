package com.hasfun.seoulsubway.event;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hasfun.seoulsubway.common.IPublicApiResult;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;
import com.hasfun.seoulsubway.dto.ReservationCultureDTO;
import com.hasfun.seoulsubway.servercall.ReservationCultureTask;
import com.hasfun.seoulsubway.servercall.SearchArrivalTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

public class ReservationCultureEvent implements OnClickListener, IPublicApiResult{
	private Activity mActivity;
	private final Logger log = Logger.getLogger(this.getClass());
	
	public ReservationCultureEvent(Activity activity){
		this.mActivity = activity;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AsyncTask<String, Void, String> data = new ReservationCultureTask(this);
		data.execute();
	}
	@Override
	public void getResult(Object object) {
		// TODO Auto-generated method stub
		ReservationCultureDTO reservationCultureDTO = (ReservationCultureDTO) object;
		ArrayList<ReservationCultureDTO.InnerData> datalist = reservationCultureDTO.getInnerdataList();
		for (int i = 0 ; i < datalist.size() ; i++ ){
			ReservationCultureDTO.InnerData data = datalist.get(i);
			log.info("======row num : " + i);
			log.info(data.getGubun());
			log.info(data.getMaxclassnm());
			log.info(data.getMinclassnm());
			log.info(data.getSvcid());
			log.info(data.getSvcnm());
			log.info(data.getSvcstatnm());
			log.info(data.getPayatnm());
			log.info(data.getPlacenm());
			log.info(data.getUsetgtinfo());
		}
	}

}
