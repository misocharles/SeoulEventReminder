package com.hasfun.seoulsubway;

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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.hasfun.seoulsubway.common.DateUtil;
import com.hasfun.seoulsubway.common.IPublicApiResult;
import com.hasfun.seoulsubway.common.SearchArrivalTask;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;


public class MainActivity extends Activity implements OnClickListener,
		IPublicApiResult {
	private final Logger log = Logger.getLogger(this.getClass());
	ArrayAdapter<String> arrayAdapter;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AsyncTask<String, Void, String> data = new SearchArrivalTask(this);
		// 지하철 코드 , (상행,내선:1, 하행,외선:2) , 요일(평일:1, 토요일:2, 휴일/일요일:3)
		String[] stationInfo = { "0151", "1", "1" };
		data.execute(stationInfo);
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				MainActivity.this);
		builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle("");
		arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
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
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Date arriveDate = sdf.parse(data.getArriveTime());
			Calendar cal = Calendar.getInstance();
			DateUtil dateUtil = new DateUtil(); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			arriveTime =data.getArriveTime(); 
			e.printStackTrace();
			
		}
		arrayAdapter.add(data.getDeststationName() + "행 : " + data.getArriveTime());
		
	}

}
