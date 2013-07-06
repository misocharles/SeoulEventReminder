package com.hasfun.seoulsubway.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.hasfun.seoulsubway.MainActivity;
import com.hasfun.seoulsubway.R;
import com.hasfun.seoulsubway.common.IPublicApiResult;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;
import com.hasfun.seoulsubway.servercall.SearchArrivalTask;

public class ArrivalTimeEvent implements OnClickListener, IPublicApiResult {
	private Activity mActivity;
	private ArrayAdapter<String> arrayAdapter;
	private final Logger log = Logger.getLogger(this.getClass());

	public ArrivalTimeEvent(Activity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		AsyncTask<String, Void, String> data = new SearchArrivalTask(this);
		// 지하철 코드 , (상행,내선:1, 하행,외선:2) , 요일(평일:1, 토요일:2, 휴일/일요일:3)
		String[] stationInfo = { "0151", "1", "3" };
		data.execute(stationInfo);
		String[] stationInfo2 = { "0151", "2", "3" };
		data.execute(stationInfo2);
		String[] stationInfo3 = { "0201", "1", "3" };
		data.execute(stationInfo3);
		String[] stationInfo4 = { "0201", "2", "3" };
		data.execute(stationInfo4);

		AlertDialog.Builder builderSingle = new AlertDialog.Builder(mActivity);
		builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle("");
		arrayAdapter = new ArrayAdapter<String>(mActivity,
				android.R.layout.select_dialog_item);
		builderSingle.setNegativeButton("닫기",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builderSingle.setAdapter(arrayAdapter, null);
		builderSingle.show();
	}
	
	@Override
	public void getResult(Object object) {
		log.info("this method called");
		ArrivalTimeDTO arrivalTimeDTO = (ArrivalTimeDTO) object;
		ArrayList<ArrivalTimeDTO.InnerData> datalist = arrivalTimeDTO
				.getInnerdataList();
		ArrivalTimeDTO.InnerData data = datalist.get(0);
		log.info("======row num : " + 0);
		log.info(data.getArriveTime());
		log.info(data.getDeststationCode());
		log.info(data.getDeststationName());
		log.info(data.getFrCode());
		log.info(data.getLeftTIme());
		log.info(data.getStationCd());
		log.info(data.getSubwayCode());
		log.info(data.getTrainCode());
		String arriveTime;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			Date firstDate = sdf2.parse(sdf.format(calendar.getTime()) + " "
					+ data.getArriveTime());
			Date secondDate = calendar.getTime();
			log.info(firstDate.toString());
			log.info(secondDate.toString());
			long times = (firstDate.getTime() - secondDate.getTime())
					/ (1000 * 60);
			arriveTime = "" + times + "분후 도착";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			arriveTime = data.getArriveTime();
			e.printStackTrace();

		}
		String lineNumber = null;
		lineNumber = arrivalTimeDTO.getStation().substring(1, 2);
		arrayAdapter.add(lineNumber + "호선 " + data.getDeststationName()
				+ "행 : " + arriveTime);
	}

}
