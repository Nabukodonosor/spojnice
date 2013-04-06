package rs.androidaplikacije.toplo_hladno;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Izbor extends Activity implements OnClickListener{
  
	Asocijacije poeni = new Asocijacije();

	Button toploHladno, asocijacije, cigle, spojnice, nazad, poeniTH, poeniAso, poeniCigle, poeniSpojnice;
	TextView naslov;
	public boolean music;
	MediaPlayer buttonClicks, buttonBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.izbor);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		music = getPrefs.getBoolean("checkbox", true);
		
		addListenerOnButton();
		
	}


	private void addListenerOnButton() {
		buttonClicks = MediaPlayer.create(this, R.raw.click);
		buttonBack = MediaPlayer.create(this, R.raw.button31);
		
		Typeface naslovType = Typeface.createFromAsset(getAssets(), "Lobster.ttf");
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
		naslov = (TextView) findViewById(R.id.tvIzborNaslov);
		toploHladno = (Button) findViewById(R.id.bIzbor1);
		asocijacije = (Button) findViewById(R.id.bIzbor2);
		cigle = (Button) findViewById(R.id.bIzbor3);
		spojnice = (Button) findViewById(R.id.bIzbor4);
		nazad = (Button) findViewById(R.id.bIzborNazad);
		poeniTH = (Button) findViewById(R.id.bPoeniTH);
		poeniAso = (Button) findViewById(R.id.bPoeniAso);
		poeniCigle = (Button) findViewById(R.id.bPoeniCigle);
		poeniSpojnice = (Button) findViewById(R.id.bPoeniSpojnice);
		naslov.setTypeface(naslovType);
		toploHladno.setTypeface(dugmad);
		asocijacije.setTypeface(dugmad);
		cigle.setTypeface(dugmad);
		spojnice.setTypeface(dugmad);
		nazad.setTypeface(dugmad);
		poeniAso.setTypeface(dugmad);
		
		toploHladno.setOnClickListener(this);
		asocijacije.setOnClickListener(this);
		cigle.setOnClickListener(this);
		spojnice.setOnClickListener(this);
		nazad.setOnClickListener(this);
		
	}
	

	@Override
	protected void onStart() {
		super.onStart(); 
		poeniAso.setText("" + poeni.brojPoenaUkupno);
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bIzbor1:
			if(music == true){
				buttonClicks.start();
					}
			startActivity(new Intent("rs.androidaplikacije.toplo_hladno.GAME"));
			break;
		case R.id.bIzbor2:
			if(music == true){
				buttonClicks.start();
					}
			startActivity(new Intent("rs.androidaplikacije.toplo_hladno.ASOCIJACIJE"));
			break;
		case R.id.bIzbor3:
			if(music == true){
				buttonClicks.start();
					}
			
			break;
		case R.id.bIzbor4:
			if(music == true){
				buttonClicks.start();
					}
			
			break;
		case R.id.bIzborNazad:
			if(music == true){
				buttonBack.start();
					}
			finish();
			break;
		}
		
	}

}
