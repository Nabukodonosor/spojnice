package rs.androidaplikacije.spojnice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import com.swarmconnect.SwarmLeaderboard;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Spojnice extends Activity implements View.OnClickListener{
  
   	    private int brojacIgara = 0;
   	    private int counter = 0;
	
		MyCount brojacVremena = new MyCount(45000, 1000);
 
    // labelForButton and tagForButton
       class MyStruct {
       public MyStruct (String lab, String t){
   	    label = lab;
   	    tag = t;
   	}
       private String label;
       private String tag;
       }
       
       final OnClickListener clickListener = new OnClickListener() {
   	    	
   	    	private Button buttonClicked;
   	    	private int brojacKlikova = 0;

   	        public void onClick(View v) {
   	        	brojacKlikova++;
   	        	if (brojacKlikova < 16) {
   	        		
   	            Button button = (Button) v;
   	            button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x003333));
   	            
   	            if (buttonClicked == null) {
   	                // first button is clicked
   	                buttonClicked = button;
   	            } else {
   	                // second button is clicked
   	                if (buttonClicked.getTag().equals(button.getTag())) {
   	                    button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x66FF33));
   	                    buttonClicked.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x66FF33));
   	                    buttonClicked.setEnabled(false);
	                    button.setEnabled(false);
	                    counter = counter + 5;
	                    score.setText("Poeni: " + counter);
   	                } else {
   	                    buttonClicked.setEnabled(false);
   	                    buttonClicked.setTextColor(Color.GRAY);
   	                    buttonClicked.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFCC99));
   	                    button.getBackground().clearColorFilter();
   	                }
   	                buttonClicked = null;
   	            }
   	        	}else{
   	        	buttonClicked = null;
   	        	brojacKlikova = 0;
   	        	brojacVremena.cancel();
   	        	mHandler.postDelayed(mLaunchTask,2200);
   	     }
   	        }
   	 };
       
	
	LinkedList<Long> mAnsweredQuestions = new LinkedList<Long>();
	
	private String generateWhereClause(){
	    StringBuilder result = new StringBuilder();
	    for (Long l : mAnsweredQuestions){
	    	result.append(" AND _ID <> " + l);
	    }
	    return result.toString();
	}
	private TextView pitanje, score, vreme;
	
	Button a1,a2,a3,a4,a5,a6,a7,a8,b1,b2,b3,b4,b5,b6,b7,b8,izlaz;
	MediaPlayer buttonBack;
	public boolean music;
	
	Runnable mLaunchTask = new Runnable() {
        public void run() {
        	nextQuestion();
        	brojacVremena.start();
        }
     };
    Runnable mLaunchTaskFinish = new Runnable() {
         public void run() {
         	finish();
         }
      };
    Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.spojnice);
		
		Typeface pitanjeType = Typeface.createFromAsset(getAssets(), "myriad.ttf");
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
		Typeface AB = Typeface.createFromAsset(getAssets(), "ARIALN.TTF");
		buttonBack = MediaPlayer.create(this, R.raw.button31);
		a1 = (Button) findViewById(R.id.bA1);
		a2 = (Button) findViewById(R.id.bA2);
		a3 = (Button) findViewById(R.id.bA3);
		a4 = (Button) findViewById(R.id.bA4);
		a5 = (Button) findViewById(R.id.bA5);
		a6 = (Button) findViewById(R.id.bA6);
		a7 = (Button) findViewById(R.id.bA7);
		a8 = (Button) findViewById(R.id.bA8);
		b1 = (Button) findViewById(R.id.bB1);
		b2 = (Button) findViewById(R.id.bB2);
		b3 = (Button) findViewById(R.id.bB3);
		b4 = (Button) findViewById(R.id.bB4);
		b5 = (Button) findViewById(R.id.bB5);
		b6 = (Button) findViewById(R.id.bB6);
		b7 = (Button) findViewById(R.id.bB7);
		b8 = (Button) findViewById(R.id.bB8);
		izlaz = (Button) findViewById(R.id.bIzlaz);
		izlaz.setTypeface(dugmad);
		score = (TextView) findViewById(R.id.tvPoeni);
		score.setTypeface(dugmad);
		vreme = (TextView) findViewById(R.id.tvVreme);
		vreme.setTypeface(dugmad);
		pitanje = (TextView) findViewById(R.id.tvPitanje);
		pitanje.setTypeface(pitanjeType);
		b1.setTypeface(AB);
		b2.setTypeface(AB);
		b3.setTypeface(AB);
		b4.setTypeface(AB);
		b5.setTypeface(AB);
		b6.setTypeface(AB);
		b7.setTypeface(AB);
		b8.setTypeface(AB);
		a1.setTypeface(AB);
		a2.setTypeface(AB);
		a3.setTypeface(AB);
		a4.setTypeface(AB);
		a5.setTypeface(AB);
		a6.setTypeface(AB);
		a7.setTypeface(AB);
		a8.setTypeface(AB);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		music = getPrefs.getBoolean("checkbox", true);
		
		nextQuestion();
		brojacVremena.start();  //startuje brojac vremena

		}
	
		public class MyCount extends CountDownTimer {

			public MyCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
			}

			@Override
			public void onFinish() {
				mHandler.postDelayed(mLaunchTask,500);
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				vreme.setText("" + millisUntilFinished / 1000);
				
			}
			
		}

	public void nextQuestion() {
		
		brojacIgara = brojacIgara + 1;
		
	
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getTestData(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));
	     
	    	ArrayList<MyStruct> labelsA = new ArrayList<MyStruct>();
	    	ArrayList<MyStruct> labelsB = new ArrayList<MyStruct>();
	        
	        labelsA.add(new MyStruct(c.getString(2), "1")); // this tag should be the same to button that matches
	        labelsB.add(new MyStruct(c.getString(3), "1"));
	        labelsA.add(new MyStruct(c.getString(4), "2"));
	        labelsB.add(new MyStruct(c.getString(5), "2"));
	        labelsA.add(new MyStruct(c.getString(6), "3"));
	        labelsB.add(new MyStruct(c.getString(7), "3"));
	        labelsA.add(new MyStruct(c.getString(8), "4"));
	        labelsB.add(new MyStruct(c.getString(9), "4"));
	        labelsA.add(new MyStruct(c.getString(10), "5"));
	        labelsB.add(new MyStruct(c.getString(11), "5"));
	        labelsA.add(new MyStruct(c.getString(12), "6"));
	        labelsB.add(new MyStruct(c.getString(13), "6"));
	        labelsA.add(new MyStruct(c.getString(14), "7"));
	        labelsB.add(new MyStruct(c.getString(15), "7"));
	        labelsA.add(new MyStruct(c.getString(16), "8"));
	        labelsB.add(new MyStruct(c.getString(17), "8"));

	        Collections.shuffle(labelsA);
	        Collections.shuffle(labelsB);
	        
	        if (brojacIgara < 4){
	        
	        pitanje.setText(c.getString(1));

	        a1.setText(labelsA.get(0).label);
	        a1.setTag(labelsA.get(0).tag);
	        a1.setOnClickListener(clickListener);
	        a1.getBackground().clearColorFilter();
	        a1.setEnabled(true);
	        b1.setText(labelsB.get(0).label);
	        b1.setTag(labelsB.get(0).tag);
	        b1.setOnClickListener(clickListener);
	        b1.getBackground().clearColorFilter();
	        b1.setEnabled(true);
	        a2.setText(labelsA.get(1).label);
	        a2.setTag(labelsA.get(1).tag);
	        a2.setOnClickListener(clickListener);
	        a2.getBackground().clearColorFilter();
	        a2.setEnabled(true);
	        b2.setText(labelsB.get(1).label);
	        b2.setTag(labelsB.get(1).tag);
	        b2.setOnClickListener(clickListener);
	        b2.getBackground().clearColorFilter();
	        b2.setEnabled(true);
	        a3.setText(labelsA.get(2).label);
	        a3.setTag(labelsA.get(2).tag);
	        a3.setOnClickListener(clickListener);
	        a3.getBackground().clearColorFilter();
	        a3.setEnabled(true);
	        b3.setText(labelsB.get(2).label);
	        b3.setTag(labelsB.get(2).tag);
	        b3.setOnClickListener(clickListener);
	        b3.getBackground().clearColorFilter();
	        b3.setEnabled(true);
	        a4.setText(labelsA.get(3).label);
	        a4.setTag(labelsA.get(3).tag);
	        a4.setOnClickListener(clickListener);
	        a4.getBackground().clearColorFilter();
	        a4.setEnabled(true);
	        b4.setText(labelsB.get(3).label);
	        b4.setTag(labelsB.get(3).tag);
	        b4.setOnClickListener(clickListener);
	        b4.getBackground().clearColorFilter();
	        b4.setEnabled(true);
	        a5.setText(labelsA.get(4).label);
	        a5.setTag(labelsA.get(4).tag);
	        a5.setOnClickListener(clickListener);
	        a5.getBackground().clearColorFilter();
	        a5.setEnabled(true);
	        b5.setText(labelsB.get(4).label);
	        b5.setTag(labelsB.get(4).tag);
	        b5.setOnClickListener(clickListener);
	        b5.getBackground().clearColorFilter();
	        b5.setEnabled(true);
	        a6.setText(labelsA.get(5).label);
	        a6.setTag(labelsA.get(5).tag);
	        a6.setOnClickListener(clickListener);
	        a6.getBackground().clearColorFilter();
	        a6.setEnabled(true);
	        b6.setText(labelsB.get(5).label);
	        b6.setTag(labelsB.get(5).tag);
	        b6.setOnClickListener(clickListener);
	        b6.getBackground().clearColorFilter();
	        b6.setEnabled(true);
	        a7.setText(labelsA.get(6).label);
	        a7.setTag(labelsA.get(6).tag);
	        a7.setOnClickListener(clickListener);
	        a7.getBackground().clearColorFilter();
	        a7.setEnabled(true);
	        b7.setText(labelsB.get(6).label);
	        b7.setTag(labelsB.get(6).tag);
	        b7.setOnClickListener(clickListener);
	        b7.getBackground().clearColorFilter();
	        b7.setEnabled(true);
	        a8.setText(labelsA.get(7).label);
	        a8.setTag(labelsA.get(7).tag);
	        a8.setOnClickListener(clickListener);
	        a8.getBackground().clearColorFilter();
	        a8.setEnabled(true);
	        b8.setText(labelsB.get(7).label);
	        b8.setTag(labelsB.get(7).tag);
	        b8.setOnClickListener(clickListener);
	        b8.getBackground().clearColorFilter();
	        b8.setEnabled(true);
	        }else{
	        	brojacVremena.cancel();
	        	Intent i = new Intent(getApplicationContext(), Rezultat.class);
		    	i.putExtra("noviRezultat", counter);
				startActivity(i);
				mHandler.postDelayed(mLaunchTaskFinish,4000);
	        	SwarmLeaderboard.submitScore(7427, counter);
	        }

		}
		
		
		finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
        	mDbHelper.close();
        }
		izlaz.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(music == true){
					buttonBack.start();
						}
				finish();	
			}
		});
	}
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		brojacVremena.cancel();
	}
	
}
