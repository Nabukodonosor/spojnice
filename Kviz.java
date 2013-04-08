package rs.androidaplikacijekvizopstekulture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Kviz extends SwarmActivity implements View.OnClickListener{

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
        }
     };
     

     Runnable mLaunchTaskFinish = new Runnable() {
         public void run() {
         	finish();
         }
      };
	
	Button bIzlazIzKviza, bOdgovor1, bOdgovor2, bOdgovor3, bOdgovor4, bPolaPola;
	TextView question, rezultat, popupRezultat, countdown;
	int counter = 0;
	public int count;
	public static String tacanOdg;
	MediaPlayer buttonBack;
	public boolean music;

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
	        	Intent i = new Intent("rs.androidaplikacijekvizopstekulture.TACANODGOVOR");
				startActivity(i);
				mHandler.postDelayed(mLaunchTask,1200);
	        }
	        else{	
	        	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
	        	i.putExtra("tacanOdgovor", tacanOdg);
	        	startActivity(i);
	        	mHandler.postDelayed(mLaunchTask,2200);
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
		Typeface pitanje = Typeface.createFromAsset(getAssets(), "myriad.ttf");
		buttonBack = MediaPlayer.create(this, R.raw.button31);
		bIzlazIzKviza = (Button) findViewById(R.id.bIzlazIzKviza);
		rezultat = (TextView) findViewById(R.id.tvBrojPitanja);
		popupRezultat = (TextView) findViewById(R.id.tvRezultat);
		bPolaPola = (Button) findViewById(R.id.bPolaPola);
		question = (TextView) findViewById(R.id.tvPitanje);
		bOdgovor1 = (Button) findViewById(R.id.bOdgovor1);
		bOdgovor2 = (Button) findViewById(R.id.bOdgovor2);
		bOdgovor3 = (Button) findViewById(R.id.bOdgovor3);
		bOdgovor4 = (Button) findViewById(R.id.bOdgovor4);
		countdown = (TextView) findViewById(R.id.tvCountdown);
		bOdgovor1.setTypeface(dugmad);
		bOdgovor2.setTypeface(dugmad);
		bOdgovor3.setTypeface(dugmad);
		bOdgovor4.setTypeface(dugmad);
		bIzlazIzKviza.setTypeface(dugmad);
		rezultat.setTypeface(dugmad);
		question.setTypeface(pitanje);
		countdown.setTypeface(dugmad);
		bPolaPola.setTypeface(dugmad);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		music = getPrefs.getBoolean("checkbox", true);

		T.scheduleAtFixedRate(new TimerTask() {         //brojac vremena
		        @Override
		        public void run() {
		            runOnUiThread(new Runnable()
		            {
		                
		                public void run()
		                {
		                    countdown.setText(""+count);
		                    count++;                
		                }
		            });
		        }
		    }, 1000, 1000);	
		
		
		nextQuestion(); //startuje prvo pitanje!
	}
	
	
	/*public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        	nextQuestion();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countdown.setText("" + millisUntilFinished / 1000);
        }
    }*/

		public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
		@Override
		protected void onStop() {
			super.onStop();
			T.cancel();
			finish();
		}
		
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
			return super.onKeyDown(keyCode, event);
		}


	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
		}

	public void nextQuestion() {
	
	    counter++;
		TestAdapter mDbHelper = new TestAdapter(this);
		//mDbHelper.createDatabase();
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getTestData(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));
	        
	        List<Answer> labels = new ArrayList<Answer>();
	        
	        labels.add(new Answer(c.getString(2), true));
	        labels.add(new Answer(c.getString(3), false));
	        labels.add(new Answer(c.getString(4), false));
	        labels.add(new Answer(c.getString(5), false));
	        
	        tacanOdg = c.getString(2);

	        Collections.shuffle(labels);
	        
	    if(counter < 11){
	    question.setText(c.getString(1));
	    
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
	    }
	    else{
	    	//brojacVremena.cancel();
	    	T.cancel();//zaustavlja brojac vremena
	    	long tEnd = System.currentTimeMillis();
	    	long tDelta = tEnd - tStart;
	    	elapsedSeconds = tDelta / 1000.0;
	    	
	    	Intent i = new Intent(getApplicationContext(), RezultatVreme.class);
	    	i.putExtra("novoVreme", elapsedSeconds);
	    	startActivity(i);
			
			float elapsedSecondsFloat = (float)elapsedSeconds;
			
			mHandler.postDelayed(mLaunchTaskFinish,3000);
			SwarmLeaderboard.submitScore(6795, elapsedSecondsFloat);
			
			
			/*Intent shareIntent=new Intent(Intent.ACTION_SEND);
		    shareIntent.setType("text/plain");
		    shareIntent.putExtra(Intent.EXTRA_TEXT,"Your score and Some extra text");
		    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "The title");
		    startActivity(Intent.createChooser(shareIntent, "Share..."));
		    
		    deli rezultat na fejsu ili twitteru ako ima instaliran na telefonu
		    */
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
			@Override
			public void onClick(View arg0) {
				int rnd1, rnd2;
				
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
		      }
