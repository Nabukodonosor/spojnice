package rs.androidaplikacije.toplo_hladno;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Popup_opis extends Activity implements OnClickListener{

  TextView tvOpis,tvNaslov,tvBrojPoena, tvResenje;
	String primljenOpis, naslov, resenje;
	int brojPoenaPrimljeno;
	Button OK;
	
	private Context context;
	
	public static void closePitanja(Context context) {
	    Intent intent = new Intent("Pitanja_Cigle");
	    intent.putExtra("action", "close");
	    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.popup_opis);
		
		Bundle extras = getIntent().getExtras(); 
	    if(extras !=null) {
	       primljenOpis = extras.getString("poslatOpis");
	       brojPoenaPrimljeno = getIntent().getIntExtra("brojPoenaPrimljeno", 0);
	       naslov = extras.getString("naslov");
	       resenje = extras.getString("resenje");
		}
		
		initVariables();
		
	}

	private void initVariables() {
		
		
		Typeface tv = Typeface.createFromAsset(getAssets(), "ARIALN.TTF");
		OK = (Button) findViewById(R.id.bOK);
		tvOpis = (TextView) findViewById(R.id.tvOpis);
		tvBrojPoena = (TextView) findViewById(R.id.tvBrojPoena);
		tvBrojPoena.setTypeface(tv);
		tvNaslov = (TextView) findViewById(R.id.tvNaslov);
		tvNaslov.setTypeface(tv);
		tvResenje = (TextView) findViewById(R.id.tvResenje);
		tvResenje.setTypeface(tv);
		tvOpis.setTypeface(tv);
		tvOpis.setText(primljenOpis);
		tvBrojPoena.setText("Osvojili ste " + brojPoenaPrimljeno + " poena u ovoj igri.");
		tvNaslov.setText(naslov);
		tvResenje.setText(resenje);
		
	
	
	OK.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			closePitanja(context);
			finish();
		}
	});
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
