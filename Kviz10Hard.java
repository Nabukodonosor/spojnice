package rs.androidaplikacije.zastaveigradovi;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

public class Kviz10Hard extends SwarmActivity implements View.OnClickListener{
  
	Button bIzlazIzKviza, bOdgovor1, bOdgovor2, bOdgovor3, bOdgovor4, bPolaPola;
	TextView rezultat, popupRezultat, countdown, score, dodatnoPitanje;
	ImageView flag;
	int counter = 0;
	public static String tacanOdg;
	int brojacTacnihOdgovora = 0;
	long mCurrentID;
	public boolean music;
	MediaPlayer buttonBack;
	
	MyCount brojacVremena = new MyCount(16000, 1000);
	MyCountDodatni brojacVremenaDodatni = new MyCountDodatni(16000, 1000);
	
	public double elapsedSeconds;
	
	Timer T=new Timer();
	long tStart = System.currentTimeMillis();
	
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
        	brojacVremena.start();
        }
     };
     Runnable mLaunchTaskGrad = new Runnable() {
         public void run() {
         	nextQuestionGrad();
         	brojacVremenaDodatni.start();
         }
      };
     Runnable mLaunchTaskValute = new Runnable() {
          public void run() {
          	nextQuestionValute();
          	brojacVremenaDodatni.start();
          }
       };

     Runnable mLaunchTaskFinish = new Runnable() {
         public void run() {
         	finish();
         }
      };
	
	private class Answer {
        public Answer(String opt, boolean correct) {
            option = opt;
            isCorrect = correct;
        }

        String option;
        boolean isCorrect;
    }
	
	Handler mHandler = new Handler();
	
	final OnClickListener clickListener = new OnClickListener() {
	    public void onClick(View v) {
	        Answer ans = (Answer) v.getTag();
	        if (ans.isCorrect) {
	        	brojacVremena.cancel();
	        	brojacTacnihOdgovora = brojacTacnihOdgovora + 5;
	        	Intent i = new Intent("rs.androidaplikacije.zastaveigradovi.TACANODGOVOR");
				startActivity(i);
				mHandler.postDelayed(mLaunchTaskGrad,1000);
	        }
	        else{
	        	brojacVremena.cancel();
	        	brojacTacnihOdgovora = brojacTacnihOdgovora - 2;
	        	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
	        	i.putExtra("tacanOdgovor", tacanOdg);
	        	startActivity(i);
	        	mHandler.postDelayed(mLaunchTask,2000);
	        }
	    }
	 };
	 final OnClickListener clickListenerGrad = new OnClickListener() {
		    public void onClick(View v) {
		        Answer ans = (Answer) v.getTag();
		        if (ans.isCorrect) {
		        	brojacVremenaDodatni.cancel();
		        	brojacTacnihOdgovora = brojacTacnihOdgovora + 5;
		        	Intent i = new Intent("rs.androidaplikacije.zastaveigradovi.TACANODGOVOR");
					startActivity(i);
					mHandler.postDelayed(mLaunchTaskValute,1000);
		        }
		        else{
		        	brojacVremenaDodatni.cancel();
		        	brojacTacnihOdgovora = brojacTacnihOdgovora - 2;
		        	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
		        	i.putExtra("tacanOdgovor", tacanOdg);
		        	startActivity(i);
		        	mHandler.postDelayed(mLaunchTask,2000);
		        }
		    }
		 };
		 final OnClickListener clickListenerValute = new OnClickListener() {
			    public void onClick(View v) {
			        Answer ans = (Answer) v.getTag();
			        if (ans.isCorrect) {
			        	brojacVremenaDodatni.cancel();
			        	brojacTacnihOdgovora = brojacTacnihOdgovora + 5;
			        	Intent i = new Intent("rs.androidaplikacije.zastaveigradovi.TACANODGOVOR");
						startActivity(i);
						mHandler.postDelayed(mLaunchTask,1000);
			        }
			        else{
			        	brojacVremenaDodatni.cancel();
			        	brojacTacnihOdgovora = brojacTacnihOdgovora - 2;
			        	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
			        	i.putExtra("tacanOdgovor", tacanOdg);
			        	startActivity(i);
			        	mHandler.postDelayed(mLaunchTask,2000);
			        }
			    }
			 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.kviz);
		
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
		Typeface pitanje = Typeface.createFromAsset(getAssets(), "Lobster.ttf");
		buttonBack = MediaPlayer.create(this, R.raw.button31);
		flag = (ImageView) findViewById(R.id.ivFlag);
		bIzlazIzKviza = (Button) findViewById(R.id.bIzlazIzKviza);
		rezultat = (TextView) findViewById(R.id.tvBrojPitanja);
		bPolaPola = (Button) findViewById(R.id.bPolaPola);
		bOdgovor1 = (Button) findViewById(R.id.bOdgovor1);
		bOdgovor2 = (Button) findViewById(R.id.bOdgovor2);
		bOdgovor3 = (Button) findViewById(R.id.bOdgovor3);
		bOdgovor4 = (Button) findViewById(R.id.bOdgovor4);
		score = (TextView) findViewById(R.id.tvSkor);
		countdown = (TextView) findViewById(R.id.tvCountdown);
		dodatnoPitanje = (TextView) findViewById(R.id.tvDodatnoPitanje);
		dodatnoPitanje.setTypeface(pitanje);
		bOdgovor1.setTypeface(dugmad);
		bOdgovor2.setTypeface(dugmad);
		bOdgovor3.setTypeface(dugmad);
		bOdgovor4.setTypeface(dugmad);
		bIzlazIzKviza.setTypeface(dugmad);
		rezultat.setTypeface(dugmad);
		score.setTypeface(dugmad);
		countdown.setTypeface(dugmad);
		bPolaPola.setTypeface(dugmad);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		music = getPrefs.getBoolean("checkbox", true);
		
		nextQuestion();
		brojacVremena.start();
		
	}
	
	 public class MyCount extends CountDownTimer {
	 public MyCount(long millisInFuture, long countDownInterval) {
         super(millisInFuture, countDownInterval);
     }
	 
     public void onFinish() {
    	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
     	i.putExtra("tacanOdgovor", tacanOdg);
     	startActivity(i);
		mHandler.postDelayed(mLaunchTask,2200);
		
		brojacTacnihOdgovora = brojacTacnihOdgovora - 2;
			
     	
     }
     public void onTick(long millisUntilFinished) {
         countdown.setText("" + millisUntilFinished / 1000);
     }
	 }
	 
	 public class MyCountDodatni extends CountDownTimer {
		 public MyCountDodatni(long millisInFuture, long countDownInterval) {
	         super(millisInFuture, countDownInterval);
	     }
		 
	     public void onFinish() {
	    	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
	     	i.putExtra("tacanOdgovor", tacanOdg);
	     	startActivity(i);
			mHandler.postDelayed(mLaunchTask,2200);
			
			brojacTacnihOdgovora = brojacTacnihOdgovora - 2;
	     	
	     }
	     public void onTick(long millisUntilFinished) {
	         countdown.setText("" + millisUntilFinished / 1000);
	     }
		 }
     
	 protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	 protected void onStop() {
		super.onStop();
		brojacVremena.cancel();
		brojacVremenaDodatni.cancel();
	}
	 
	 private void nextQuestionValute() {
		 
		 flag.setVisibility(View.GONE);
		 dodatnoPitanje.setVisibility(View.VISIBLE);

		 TestAdapter mDbHelper = new TestAdapter(this);
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			
			if(!myDbHelper.checkDataBase()){
			mDbHelper.createDatabase();
			}
			
			try{    //Pokusava da otvori db
	        	
		        mDbHelper.open();  //baza otvorena
		        
		        Cursor c = mDbHelper.getTestDataValute(mCurrentID);
		        
		        List<Answer> labels = new ArrayList<Answer>();
		        
		        labels.add(new Answer(c.getString(2), true));
		        labels.add(new Answer(c.getString(3), false));
		        labels.add(new Answer(c.getString(4), false));
		        labels.add(new Answer(c.getString(5), false));
		        
		        tacanOdg = c.getString(2);

		        Collections.shuffle(labels);

		        dodatnoPitanje.setText(c.getString(1));
		        
		        bOdgovor1.setText(labels.get(0).option);
			    bOdgovor1.setTag(labels.get(0));
		        bOdgovor1.setOnClickListener(clickListenerValute);
		        
		        bOdgovor2.setText(labels.get(1).option);
		        bOdgovor2.setTag(labels.get(1));
		        bOdgovor2.setOnClickListener(clickListenerValute);
		        
		        bOdgovor3.setText(labels.get(2).option);
		        bOdgovor3.setTag(labels.get(2));
		        bOdgovor3.setOnClickListener(clickListenerValute);
		        
		        bOdgovor4.setText(labels.get(3).option);
		        bOdgovor4.setTag(labels.get(3));
		        bOdgovor4.setOnClickListener(clickListenerValute);
		        
			    score.setText("Score: " + brojacTacnihOdgovora);

			}
			finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
	        	mDbHelper.close();
	        }
			
		}
	 
	 private void nextQuestionGrad() {
		 
		 flag.setVisibility(View.GONE);
		 dodatnoPitanje.setVisibility(View.VISIBLE);

		 TestAdapter mDbHelper = new TestAdapter(this);
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			
			if(!myDbHelper.checkDataBase()){
			mDbHelper.createDatabase();
			}
			
			try{    //Pokusava da otvori db
	        	
		        mDbHelper.open();  //baza otvorena
		        
		        Cursor c = mDbHelper.getTestDataGradovi(mCurrentID);
		        c.moveToFirst();
		        
		        List<Answer> labels = new ArrayList<Answer>();
		        
		        labels.add(new Answer(c.getString(2), true));
		        labels.add(new Answer(c.getString(3), false));
		        labels.add(new Answer(c.getString(4), false));
		        labels.add(new Answer(c.getString(5), false));
		        
		        tacanOdg = c.getString(2);

		        Collections.shuffle(labels);

		        dodatnoPitanje.setText(c.getString(1));
		        
		        bOdgovor1.setText(labels.get(0).option);
			    bOdgovor1.setTag(labels.get(0));
		        bOdgovor1.setOnClickListener(clickListenerGrad);
		        
		        bOdgovor2.setText(labels.get(1).option);
		        bOdgovor2.setTag(labels.get(1));
		        bOdgovor2.setOnClickListener(clickListenerGrad);
		        
		        bOdgovor3.setText(labels.get(2).option);
		        bOdgovor3.setTag(labels.get(2));
		        bOdgovor3.setOnClickListener(clickListenerGrad);
		        
		        bOdgovor4.setText(labels.get(3).option);
		        bOdgovor4.setTag(labels.get(3));
		        bOdgovor4.setOnClickListener(clickListenerGrad);
		        
			    score.setText("Score: " + brojacTacnihOdgovora);

			}
			finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
	        	mDbHelper.close();
	        }
	 }

	private void nextQuestion() {
		dodatnoPitanje.setVisibility(View.GONE);
		flag.setVisibility(View.VISIBLE);
		counter++;
		
		TestAdapter mDbHelper = new TestAdapter(this);
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		
		if(!myDbHelper.checkDataBase()){
		mDbHelper.createDatabase();
		}
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getTestData(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));
	        
	        mCurrentID = c.getLong(0);
	        
	        List<Answer> labels = new ArrayList<Answer>();
	        
	        labels.add(new Answer(c.getString(2), true));
	        labels.add(new Answer(c.getString(3), false));
	        labels.add(new Answer(c.getString(4), false));
	        labels.add(new Answer(c.getString(5), false));
	        
	        tacanOdg = c.getString(2);
	        
	        
	        Collections.shuffle(labels);
	        
	        byte[] image_bytes = c.getBlob(c.getColumnIndex("PITANJE"));
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(image_bytes); 
	        Bitmap bp=BitmapFactory.decodeStream(inputStream);
	        
	        if(counter < 11){
	        flag.setImageBitmap(bp);
	        
	        bOdgovor1.setText(labels.get(0).option);
		    bOdgovor1.setTag(labels.get(0));
	        bOdgovor1.setOnClickListener(clickListener);
	        
	        bOdgovor2.setText(labels.get(1).option);
	        bOdgovor2.setTag(labels.get(1));
	        bOdgovor2.setOnClickListener(clickListener);
	        
	        bOdgovor3.setText(labels.get(2).option);
	        bOdgovor3.setTag(labels.get(2));
	        bOdgovor3.setOnClickListener(clickListener);
	        
	        bOdgovor4.setText(labels.get(3).option);
	        bOdgovor4.setTag(labels.get(3));
	        bOdgovor4.setOnClickListener(clickListener);
	        
		    rezultat.setText(counter + "/10");
		    score.setText("Score: " + brojacTacnihOdgovora);
		
	        }else{
	        	brojacVremena.cancel();
		    	Intent i = new Intent(getApplicationContext(), Rezultat.class);
		    	i.putExtra("noviRezultat", brojacTacnihOdgovora);
				startActivity(i);
				mHandler.postDelayed(mLaunchTaskFinish,4000);
				SwarmLeaderboard.submitScore(7849, brojacTacnihOdgovora);
	        }
		}
		finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
        	mDbHelper.close();
        }
		
		bIzlazIzKviza.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(music == true){
					buttonBack.start();
						}
				finish();	
			}
		});
		
		bPolaPola.setOnClickListener(new OnClickListener() {	
			
			public void onClick(View arg0) {
				int rnd1, rnd2;
				
				if(music == true){
					buttonBack.start();
						}
				
				List<Integer> list = new ArrayList<Integer>();				
				
				if (!((Answer)bOdgovor1.getTag()).isCorrect) {
					list.add(1);
				}
				if (!((Answer)bOdgovor2.getTag()).isCorrect) {
					list.add(2);
				}
				if (!((Answer)bOdgovor3.getTag()).isCorrect) {
					list.add(3);
				}
				if (!((Answer)bOdgovor4.getTag()).isCorrect) {
					list.add(4);
				}	
				Collections.shuffle(list);
				rnd1 = list.get(0);
				rnd2 = list.get(1);
				
				if ((rnd1 == 1) || (rnd2 == 1)){
					bOdgovor1.setText("");
				}
				if ((rnd1 == 2) || (rnd2 == 2)){
					bOdgovor2.setText("");
				}
				if ((rnd1 == 3) || (rnd2 == 3)){
					bOdgovor3.setText("");
				}
				if ((rnd1 == 4) || (rnd2 == 4)){
					bOdgovor4.setText("");
				}
				bPolaPola.setText("X");
				bPolaPola.setEnabled(false);
			}
			
		});

}
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
