package rs.androidaplikacije.toplo_hladno;

import java.text.Normalizer;
import java.util.LinkedList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Asocijacije extends Activity implements OnClickListener{
	
	Button a1, a2, a3, a4, kolonaA, b1, b2, b3, b4, kolonaB, c1, c2, c3, c4, kolonaC, d1, d2, d3, d4, kolonaD, konacnoResenje,
	izlaz, dalje;
	MediaPlayer buttonClicks, buttonKolone;
	String mB1, mB2, mB3, mB4, mBKolonaB, mA1, mA2, mA3, mA4, mAKolonaA, mC1, mC2, mC3, mC4, 
	mCKolonaC, mD1, mD2, mD3, mD4, mDKolonaD, mKonacno, konacanNormalized, kolonaANormalized, kolonaBNormalized,
	kolonaCNormalized, kolonaDNormalized;
	boolean music;
	int brojPoenaAsocijacije, ukupnoZaDugmadB, ukupnoZaDugmadA, ukupnoZaDugmadC, ukupnoZaDugmadD, ukupnoZaDugmad, 
	poeniZaDugmadA, poeniZaDugmadB, poeniZaDugmadC, poeniZaDugmadD;
	public static int brojPoenaUkupnoAs;

	int counter = 0;
	final Context context = this;
	Editable ukucanRezultatA, ukucanRezultatB, ukucanRezultatC, ukucanRezultatD, ukucanRezultatK;
	TextView vreme;
	
	MyCount brojacVremena = new MyCount(30000, 1000);
	Handler mHandler = new Handler();
	
	LinkedList<Long> mAnsweredQuestions = new LinkedList<Long>();
	
	private String generateWhereClause(){
	    StringBuilder result = new StringBuilder();
	    for (Long l : mAnsweredQuestions){
	    	result.append(" AND _ID <> " + l);
	    }
	    return result.toString();
	}
	
	Runnable mLaunchTask = new Runnable() {
        public void run() {
        	nextQuestion();
        }
     };
    Runnable mLaunchTaskFinish = new Runnable() {
         public void run() {
         	finish();
         }
      };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.asocijacije);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		music = getPrefs.getBoolean("checkbox", true);
		
		InicirajVariable();
		
		nextQuestion();
	}
	
	public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        	brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmad;
    		brojPoenaUkupnoAs = brojPoenaUkupnoAs + brojPoenaAsocijacije;
    		
        	Intent i = new Intent(Asocijacije.this, Isteklo_vreme.class);
        	startActivity(i);
        	setResults();
        	if(counter<3){
        		dalje.setEnabled(true);
        		dalje.setText("Dalje!");
        		disejblujDugmad();
        		disejblujKolone();
        		//mHandler.postDelayed(mLaunchTask,6500);
        	}else{
        		Intent resp = new Intent();
	        	resp.putExtra("score", brojPoenaUkupnoAs);
	        	setResult(1, resp);
	        	
	        	mHandler.postDelayed(mLaunchTaskFinish,6500);
        	}
        }

        @Override
        public void onTick(long millisUntilFinished) {
            vreme.setText("" + millisUntilFinished / 1000);
        }
    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		brojacVremena.cancel();
	}

	private void setResults(){
		
		a1.setText(mA1);
		a2.setText(mA2);
		a3.setText(mA3);
		a4.setText(mA4);
		b1.setText(mB1);
		b2.setText(mB2);
		b3.setText(mB3);
		b4.setText(mB4);
		c1.setText(mC1);
		c2.setText(mC2);
		c3.setText(mC3);
		c4.setText(mC4);
		d1.setText(mD1);
		d2.setText(mD2);
		d3.setText(mD3);
		d4.setText(mD4);
		kolonaA.setText(mAKolonaA);
		kolonaB.setText(mBKolonaB);
		kolonaC.setText(mCKolonaC);
		kolonaD.setText(mDKolonaD);
		konacnoResenje.setText(mKonacno);
		
		
		a1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		a2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		a3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		a4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		kolonaA.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		
		b1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		b2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		b3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		b4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		kolonaB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		
		c1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		c2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		c3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		c4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		kolonaC.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		
		d1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		d2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		d3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		d4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		kolonaD.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		
		konacnoResenje.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
		
	}
	
	private void pocetniTekst(){
		a1.setText("A1");
        a2.setText("A2");
        a3.setText("A3");
        a4.setText("A4");
        b1.setText("B1");
        b2.setText("B2");
        b3.setText("B3");
        b4.setText("B4");
        c1.setText("C1");
        c2.setText("C2");
        c3.setText("C3");
        c4.setText("C4");
        d1.setText("D1");
        d2.setText("D2");
        d3.setText("D3");
        d4.setText("D4");
        kolonaA.setText("Kolona A");
        kolonaB.setText("Kolona B");
        kolonaC.setText("Kolona C");
        kolonaD.setText("Kolona D");
        konacnoResenje.setText("Konačno rešenje!");
	}

	private void nextQuestion() {
		
		counter++;
		brojPoenaAsocijacije = 0;
		poeniZaDugmadA = 0;
		poeniZaDugmadB = 0;
		poeniZaDugmadC = 0;
		poeniZaDugmadD = 0;
		ukupnoZaDugmadA = 0;
		ukupnoZaDugmadB = 0;
		ukupnoZaDugmadC = 0;
		ukupnoZaDugmadD = 0;
		ukupnoZaDugmad = 0;
		
		brojacVremena.start();  //startuje brojac vremena
		
		TestAdapter mDbHelper = new TestAdapter(this);
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		
		if(!myDbHelper.checkDataBase()){
		mDbHelper.createDatabase();
		}
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getAsocijacije(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));
	        
	        if(counter < 3){
	        
	        pocetniTekst();
	        
	        a1.getBackground().clearColorFilter();
	        a2.getBackground().clearColorFilter();
	        a3.getBackground().clearColorFilter();
	        a4.getBackground().clearColorFilter();
	        b1.getBackground().clearColorFilter();
	        b2.getBackground().clearColorFilter();
	        b3.getBackground().clearColorFilter();
	        b4.getBackground().clearColorFilter();
	        c1.getBackground().clearColorFilter();
	        c2.getBackground().clearColorFilter();
	        c3.getBackground().clearColorFilter();
	        c4.getBackground().clearColorFilter();
	        d1.getBackground().clearColorFilter();
	        d2.getBackground().clearColorFilter();
	        d3.getBackground().clearColorFilter();
	        d4.getBackground().clearColorFilter();
	        kolonaA.getBackground().clearColorFilter();
	        kolonaB.getBackground().clearColorFilter();
	        kolonaC.getBackground().clearColorFilter();
	        kolonaD.getBackground().clearColorFilter();
	        konacnoResenje.getBackground().clearColorFilter();
	        	
	        
	        mA1 = c.getString(6);
	        mA2 = c.getString(7);
	        mA3 = c.getString(8);
	        mA4 = c.getString(9);
	        mB1 = c.getString(10);
	        mB2 = c.getString(11);
	        mB3 = c.getString(12);
	        mB4 = c.getString(13);
	        mC1 = c.getString(14);
	        mC2 = c.getString(15);
	        mC3 = c.getString(16);
	        mC4 = c.getString(17);
	        mD1 = c.getString(18);
	        mD2 = c.getString(19);
	        mD3 = c.getString(20);
	        mD4 = c.getString(21);
	        mAKolonaA = c.getString(2);
	        mBKolonaB = c.getString(3);
	        mCKolonaC = c.getString(4);
	        mDKolonaD = c.getString(5);
	        mKonacno = c.getString(1).toUpperCase();
	        
	        konacanNormalized = Normalizer.normalize(mKonacno, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	        kolonaANormalized = Normalizer.normalize(mAKolonaA, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	        kolonaBNormalized = Normalizer.normalize(mBKolonaB, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	        kolonaCNormalized = Normalizer.normalize(mCKolonaC, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	        kolonaDNormalized = Normalizer.normalize(mDKolonaD, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	        
	        a1.setOnClickListener(this);
	        a2.setOnClickListener(this);
	        a3.setOnClickListener(this);
	        a4.setOnClickListener(this);
	        b1.setOnClickListener(this);
	        b2.setOnClickListener(this);
	        b3.setOnClickListener(this);
	        b4.setOnClickListener(this);
	        c1.setOnClickListener(this);
	        c2.setOnClickListener(this);
	        c3.setOnClickListener(this);
	        c4.setOnClickListener(this);
	        d1.setOnClickListener(this);
	        d2.setOnClickListener(this);
	        d3.setOnClickListener(this);
	        d4.setOnClickListener(this);
	        kolonaA.setOnClickListener(this);
	        kolonaB.setOnClickListener(this);
	        kolonaC.setOnClickListener(this);
	        kolonaD.setOnClickListener(this);
	        izlaz.setOnClickListener(this);
	        konacnoResenje.setOnClickListener(this);
	        }else{
	        	brojacVremena.cancel();
	        	Intent resp = new Intent();
	        	resp.putExtra("score", brojPoenaUkupnoAs);
	        	setResult(1, resp);
	        	finish();
	        	
	        }
		}
        
        finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
        	mDbHelper.close();
        }
	}

	private void InicirajVariable() {
		buttonClicks = MediaPlayer.create(this, R.raw.button);
		buttonKolone = MediaPlayer.create(this, R.raw.click);
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "ARIALN.TTF");
		
		a1 = (Button) findViewById(R.id.bA1);
		a2 = (Button) findViewById(R.id.bA2);
		a3 = (Button) findViewById(R.id.bA3);
		a4 = (Button) findViewById(R.id.bA4);
		b1 = (Button) findViewById(R.id.bB1);
		b2 = (Button) findViewById(R.id.bB2);
		b3 = (Button) findViewById(R.id.bB3);
		b4 = (Button) findViewById(R.id.bB4);
		c1 = (Button) findViewById(R.id.bC1);
		c2 = (Button) findViewById(R.id.bC2);
		c3 = (Button) findViewById(R.id.bC3);
		c4 = (Button) findViewById(R.id.bC4);
		d1 = (Button) findViewById(R.id.bD1);
		d2 = (Button) findViewById(R.id.bD2);
		d3 = (Button) findViewById(R.id.bD3);
		d4 = (Button) findViewById(R.id.bD4);
		kolonaA = (Button) findViewById(R.id.bKolonaA);
		kolonaB = (Button) findViewById(R.id.bKolonaB);
		kolonaC = (Button) findViewById(R.id.bKolonaC);
		kolonaD = (Button) findViewById(R.id.bKolonaD);
		konacnoResenje = (Button) findViewById(R.id.bKonacno);
		vreme = (TextView) findViewById(R.id.tvVreme);
		izlaz = (Button) findViewById(R.id.bIzlazA);
		dalje = (Button) findViewById(R.id.bDaljeA);
		
		a1.setTypeface(dugmad);
		a2.setTypeface(dugmad);
		a3.setTypeface(dugmad);
		a4.setTypeface(dugmad);
		b1.setTypeface(dugmad);
		b2.setTypeface(dugmad);
		b3.setTypeface(dugmad);
		b4.setTypeface(dugmad);
		c1.setTypeface(dugmad);
		c2.setTypeface(dugmad);
		c3.setTypeface(dugmad);
		c4.setTypeface(dugmad);
		d1.setTypeface(dugmad);
		d2.setTypeface(dugmad);
		d3.setTypeface(dugmad);
		d4.setTypeface(dugmad);
		kolonaA.setTypeface(dugmad);
		kolonaB.setTypeface(dugmad);
		kolonaC.setTypeface(dugmad);
		kolonaD.setTypeface(dugmad);
		konacnoResenje.setTypeface(dugmad);
		izlaz.setTypeface(dugmad);
		dalje.setTypeface(dugmad);
		
		dalje.setEnabled(false);
		dalje.setText("");
		
		kolonaA.setEnabled(false);
		kolonaB.setEnabled(false);
		kolonaC.setEnabled(false);
		kolonaD.setEnabled(false);
		
		dalje.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				nextQuestion();
				pocetniTekst();
				enejblujDugmad();
				disejblujKolone();
			}
			
		});
	}
	
	private void enejblujKolone(){
		kolonaA.setEnabled(true);
		kolonaB.setEnabled(true);
		kolonaC.setEnabled(true);
		kolonaD.setEnabled(true);
	}
	
	private void disejblujKolone(){
		kolonaA.setEnabled(false);
		kolonaB.setEnabled(false);
		kolonaC.setEnabled(false);
		kolonaD.setEnabled(false);
	}
	
	private void enejblujDugmad(){
		a1.setEnabled(true);
		a2.setEnabled(true);
		a3.setEnabled(true);
		a4.setEnabled(true);
		b1.setEnabled(true);
		b2.setEnabled(true);
		b3.setEnabled(true);
		b4.setEnabled(true);
		c1.setEnabled(true);
		c2.setEnabled(true);
		c3.setEnabled(true);
		c4.setEnabled(true);
		d1.setEnabled(true);
		d2.setEnabled(true);
		d3.setEnabled(true);
		d4.setEnabled(true);
		konacnoResenje.setEnabled(true);
	}
	
	private void disejblujDugmad(){
		a1.setEnabled(false);
		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		c1.setEnabled(false);
		c2.setEnabled(false);
		c3.setEnabled(false);
		c4.setEnabled(false);
		d1.setEnabled(false);
		d2.setEnabled(false);
		d3.setEnabled(false);
		d4.setEnabled(false);
		konacnoResenje.setEnabled(false);
	}

	public void onClick(View v) {
		
		switch(v.getId()){

		case R.id.bIzlazA:
			finish();
			brojacVremena.cancel();
			break;
		case R.id.bA1:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadA = poeniZaDugmadA + 1;
			a1.setText(mA1);
			a1.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bA2:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadA = poeniZaDugmadA + 1;
			a2.setText(mA2);
			a2.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bA3:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadA = poeniZaDugmadA + 1;
			a3.setText(mA3);
			a3.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bA4:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadA = poeniZaDugmadA + 1;
			a4.setText(mA4);
			a4.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bB1:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadB = poeniZaDugmadB + 1;
			b1.setText(mB1);
			b1.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bB2:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadB = poeniZaDugmadB + 1;
			b2.setText(mB2);
			b2.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bB3:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadB = poeniZaDugmadB + 1;
			b3.setText(mB3);
			b3.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bB4:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadB = poeniZaDugmadB + 1;
			b4.setText(mB4);
			b4.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bC1:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadC = poeniZaDugmadC + 1;
			c1.setText(mC1);
			c1.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bC2:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadC = poeniZaDugmadC + 1;
			c2.setText(mC2);
			c2.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bC3:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadC = poeniZaDugmadC + 1;
			c3.setText(mC3);
			c3.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bC4:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadC = poeniZaDugmadC + 1;
			c4.setText(mC4);
			c4.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bD4:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadD = poeniZaDugmadD + 1;
			d4.setText(mD4);
			d4.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bD3:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadD = poeniZaDugmadD + 1;
			d3.setText(mD3);
			d3.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bD2:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadD = poeniZaDugmadD + 1;
			d2.setText(mD2);
			d2.setEnabled(false);
			enejblujKolone();
			break;
		case R.id.bD1:
			if(music == true){
				buttonClicks.start();
					}
			poeniZaDugmadD = poeniZaDugmadD + 1;
			d1.setText(mD1);
			d1.setEnabled(false);
			enejblujKolone();
			break;
			
		case R.id.bKolonaB:
			if(music == true){
				buttonKolone.start();
					}
			
			disejblujKolone();

			
			LayoutInflater layoutInflaterB = LayoutInflater.from(context);	
            View promptViewB = layoutInflaterB.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilderB = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilderB.setView(promptViewB);
            final EditText inputB = (EditText) promptViewB.findViewById(R.id.userInput);
            
            InputMethodManager immB = (InputMethodManager)
            Asocijacije.this.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (immB != null){
            immB.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
            
            alertDialogBuilderB
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultatB = inputB.getText();
                                                	String ukucanRezultatStrB = ukucanRezultatB.toString().toUpperCase();                 	
                                                	
                                                	if (ukucanRezultatStrB.equals(kolonaBNormalized)){
                                                		
                                                		ukupnoZaDugmadB = 4 - poeniZaDugmadB;
                                                		brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmadB + 5;
                                                		
                                                		kolonaB.setText(mBKolonaB);
                                            			
                                            			b1.setText(mB1);
                                            			b2.setText(mB2);
                                            			b3.setText(mB3);
                                            			b4.setText(mB4);
                                            			kolonaB.setText(mBKolonaB);
                                            			
                                            			kolonaB.setEnabled(false);
                                            			
                                            			b1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			b2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			b3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			b4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			kolonaB.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			
                                                 }else{
                                                  	Toast.makeText(Asocijacije.this, "Pogrešan odgovor!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                           });
                            // create an alert dialog

                            AlertDialog alertB = alertDialogBuilderB.create();
                            alertB.show();
           
                            break;
                            
		case R.id.bKolonaA:
			if(music == true){
				buttonKolone.start();
					}
			
			disejblujKolone();
			
			LayoutInflater layoutInflaterA = LayoutInflater.from(context);	
            View promptViewA = layoutInflaterA.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilderA = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilderA.setView(promptViewA);
            final EditText inputA = (EditText) promptViewA.findViewById(R.id.userInput);
            
            InputMethodManager immA = (InputMethodManager)
            Asocijacije.this.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (immA != null){
            immA.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
            
            alertDialogBuilderA
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultatA = inputA.getText();
                                                	String ukucanRezultatStrA = ukucanRezultatA.toString().toUpperCase();                 	
                                                	
                                                	if (ukucanRezultatStrA.equals(kolonaANormalized)){
                                                		
                                                		ukupnoZaDugmadA = 4 - poeniZaDugmadA;
                                                		brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmadA + 5;
                                                		
                                                		kolonaA.setText(mAKolonaA);
                                            			
                                            			a1.setText(mA1);
                                            			a2.setText(mA2);
                                            			a3.setText(mA3);
                                            			a4.setText(mA4);
                                            			kolonaA.setText(mAKolonaA);
                                            			
                                            			kolonaA.setEnabled(false);
                                            			
                                            			a1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			a2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			a3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			a4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			kolonaA.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			
                                                 }else{
                                                  	Toast.makeText(Asocijacije.this, "Pogrešan odgovor!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                           });
                            // create an alert dialog

                            AlertDialog alertA = alertDialogBuilderA.create();
                            alertA.show();
           
                            break;
                            
		case R.id.bKolonaC:
			if(music == true){
				buttonKolone.start();
					}
			
			disejblujKolone();
			
			LayoutInflater layoutInflaterC = LayoutInflater.from(context);	
            View promptViewC = layoutInflaterC.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilderC = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilderC.setView(promptViewC);
            final EditText inputC = (EditText) promptViewC.findViewById(R.id.userInput);
            
            InputMethodManager immC = (InputMethodManager)
            Asocijacije.this.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (immC != null){
            immC.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
            
            alertDialogBuilderC
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultatC = inputC.getText();
                                                	String ukucanRezultatStrC = ukucanRezultatC.toString().toUpperCase();                   	
                                                	
                                                	if (ukucanRezultatStrC.equals(kolonaCNormalized)){
                                                		
                                                		ukupnoZaDugmadC = 4 - poeniZaDugmadC;
                                                		brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmadC + 5;
                                                		
                                                		kolonaC.setText(mCKolonaC);
                                            			
                                            			c1.setText(mC1);
                                            			c2.setText(mC2);
                                            			c3.setText(mC3);
                                            			c4.setText(mC4);
                                            			kolonaC.setText(mCKolonaC);
                                            			
                                            			kolonaC.setEnabled(false);
                                            			
                                            			c1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			c2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			c3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			c4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			kolonaC.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			
                                                 }else{
                                                  	Toast.makeText(Asocijacije.this, "Pogrešan odgovor!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                           });
                            // create an alert dialog

                            AlertDialog alertC = alertDialogBuilderC.create();
                            alertC.show();
           
                            break;
		case R.id.bKolonaD:
			if(music == true){
				buttonKolone.start();
					}
			
			disejblujKolone();
			
			LayoutInflater layoutInflaterD = LayoutInflater.from(context);	
            View promptViewD = layoutInflaterD.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilderD = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilderD.setView(promptViewD);
            final EditText inputD = (EditText) promptViewD.findViewById(R.id.userInput);
            
            InputMethodManager immD = (InputMethodManager)
            Asocijacije.this.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (immD != null){
            immD.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
            
            alertDialogBuilderD
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultatD = inputD.getText();
                                                	String ukucanRezultatStrD = ukucanRezultatD.toString().toUpperCase();                  	
                                                	
                                                	if (ukucanRezultatStrD.equals(kolonaDNormalized)){
                                                		
                                                		ukupnoZaDugmadD = 4 - poeniZaDugmadD;
                                                		brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmadD + 5;
                                                		
                                                		kolonaD.setText(mDKolonaD);
                                            			
                                            			d1.setText(mD1);
                                            			d2.setText(mD2);
                                            			d3.setText(mD3);
                                            			d4.setText(mD4);
                                            			kolonaD.setText(mDKolonaD);
                                            			
                                            			kolonaD.setEnabled(false);
                                            			
                                            			d1.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			d2.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			d3.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			d4.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			kolonaD.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x638000));
                                            			
                                                 }else{
                                                  	Toast.makeText(Asocijacije.this, "Pogrešan odgovor!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                           });
                            // create an alert dialog

                            AlertDialog alertD = alertDialogBuilderD.create();
                            alertD.show();
           
                            break;
		case R.id.bKonacno:
			if(music == true){
				buttonKolone.start();
					}
			
			disejblujKolone();
			
			LayoutInflater layoutInflaterK = LayoutInflater.from(context);	
            View promptViewK = layoutInflaterK.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilderK = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilderK.setView(promptViewK);
            final EditText inputK = (EditText) promptViewK.findViewById(R.id.userInput);
            
            alertDialogBuilderK
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultatK = inputK.getText();
                                                	String ukucanRezultatStrK = ukucanRezultatK.toString().toUpperCase();                 	
                                                	
                                                	if (ukucanRezultatStrK.equals(konacanNormalized)){
                                                		
                                                		if(kolonaA.getText().equals("Kolona A")){
                                                			ukupnoZaDugmad = ukupnoZaDugmad + 8 - poeniZaDugmadA;
                                                		}
                                                		if(kolonaB.getText().equals("Kolona B")){
                                                			ukupnoZaDugmad = ukupnoZaDugmad + 8 - poeniZaDugmadB;
                                                		}
                                                		if(kolonaC.getText().equals("Kolona C")){
                                                			ukupnoZaDugmad = ukupnoZaDugmad + 8 - poeniZaDugmadC;
                                                		}
                                                		if(kolonaD.getText().equals("Kolona D")){
                                                			ukupnoZaDugmad = ukupnoZaDugmad + 8 - poeniZaDugmadD;
                                                		}
                                                		brojPoenaAsocijacije = brojPoenaAsocijacije + ukupnoZaDugmad + 15;
                                                		
                                                		brojPoenaUkupnoAs = brojPoenaUkupnoAs + brojPoenaAsocijacije;
                                                		
                                                		konacnoResenje.setText(mKonacno);
                                                		
                                                		brojacVremena.cancel();
                                            			
                                            			Intent i = new Intent(Asocijacije.this, Popup_asocijacije.class);
                                                		i.putExtra("brojPoenaPrimljeno", brojPoenaAsocijacije);
                                            			startActivity(i);
                                            			
                                                		//mHandler.postDelayed(mLaunchTask,3500);
                                            			dalje.setEnabled(true);
                                                		dalje.setText("Dalje!");
                                                		
                                            			setResults();

                                                 }else{
                                                  	Toast.makeText(Asocijacije.this, "Pogrešan odgovor!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                           });
                            // create an alert dialog

                            AlertDialog alertK = alertDialogBuilderK.create();
                            alertK.show();
           
                            break;
                        }	
	}
}
