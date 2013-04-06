package rs.androidaplikacije.toplo_hladno;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Popup_asocijacije extends Activity implements OnClickListener{
  
	TextView tvNaslov, tvBrojPoena;
	int brojPoenaPrimljeno;
	
	public final int delayTime = 3000;
	private Handler myHandler = new Handler();
	
	public void onUserInteraction(){
	    myHandler.removeCallbacks(zatvoriPopup);
	    myHandler.postDelayed(zatvoriPopup, delayTime);
	}
	private Runnable zatvoriPopup = new Runnable(){
	    public void run(){
	        finish();
	    }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.popup_asocijacije);
		
		 Intent mIntent = getIntent(); 
	        if(mIntent !=null) {
	        	brojPoenaPrimljeno = getIntent().getIntExtra("brojPoenaPrimljeno", 0);
	        }
	        
	        initVariables();
	        
	        myHandler.postDelayed(zatvoriPopup, delayTime);
	}

	private void initVariables() {
		Typeface tv = Typeface.createFromAsset(getAssets(), "ARIALN.TTF");
		tvNaslov = (TextView) findViewById(R.id.tvNaslovA);
		tvBrojPoena = (TextView) findViewById(R.id.tvBrojPoenaA);
		tvBrojPoena.setTypeface(tv);
		tvNaslov.setTypeface(tv);
		
		tvBrojPoena.setText("Osvojili ste " + brojPoenaPrimljeno + " poena u ovoj igri.");
	}
		

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
