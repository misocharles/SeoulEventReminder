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

import com.hasfun.seoulsubway.common.IPublicApiResult;
import com.hasfun.seoulsubway.dto.ArrivalTimeDTO;
import com.hasfun.seoulsubway.event.ArrivalTimeEvent;
import com.hasfun.seoulsubway.event.ReservationCultureEvent;
import com.hasfun.seoulsubway.servercall.SearchArrivalTask;


public class MainActivity extends Activity {
	private final Logger log = Logger.getLogger(this.getClass());
	ArrayAdapter<String> arrayAdapter;
	Button button;
	Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new ArrivalTimeEvent(this));
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new ReservationCultureEvent(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
