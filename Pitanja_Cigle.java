package rs.androidaplikacije.toplo_hladno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Pitanja_Cigle extends Activity{
  
	public static String tacanOdg;
	int counter = 0;
	
	Button b1, b2, b3, b4;
	TextView question;
	boolean tacno = true;
	boolean pogresno = false;
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String action= intent.getStringExtra("action");
	        if(action.equals("close")) { 
	        	Intent resp = new Intent(); 
	        	resp.putExtra("score", pogresno); 
	        	Pitanja_Cigle.this.setResult(0, resp);
	        	Pitanja_Cigle.this.finish(); 
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
	        	Intent resp = new Intent();
	        	resp.putExtra("score", tacno);
	        	setResult(1, resp);
	        	Intent i = new Intent("rs.androidaplikacije.toplo_hladno.TACANODGOVOR");
				startActivity(i);
				mHandler.postDelayed(mLaunchTaskFinish,1200);
	        }
	        else{
	        	Intent resp = new Intent();
	        	resp.putExtra("score", pogresno);
	        	setResult(1, resp);
	        	Intent i = new Intent(getApplicationContext(), PogresanOdgovor.class);
	        	i.putExtra("tacanOdgovor", tacanOdg);
	        	startActivity(i);
	        	mHandler.postDelayed(mLaunchTaskFinish,2200);
	        }
	    }
	 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("Pitanja_Cigle"));

		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.pitanja_cigle);
		
		InicirajVariable();
		
		nextQuestion();
	}

	private void nextQuestion() {
		counter++;
		
		TestAdapter mDbHelper = new TestAdapter(this);
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		
		if(!myDbHelper.checkDataBase()){
		mDbHelper.createDatabase();
		}
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getPitanjaCigle(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));
	        
	        List<Answer> labels = new ArrayList<Answer>();
	        
	        labels.add(new Answer(c.getString(2), true));
	        labels.add(new Answer(c.getString(3), false));
	        labels.add(new Answer(c.getString(4), false));
	        labels.add(new Answer(c.getString(5), false));
	        
	        tacanOdg = c.getString(2);

	        Collections.shuffle(labels);
		
	        question.setText(c.getString(1));
	        
	        b1.setText(labels.get(0).option);
		    b1.setTag(labels.get(0));
	        b1.setOnClickListener(clickListener);
	        
	        b2.setText(labels.get(1).option);
	        b2.setTag(labels.get(1));
	        b2.setOnClickListener(clickListener);
	        
	        b3.setText(labels.get(2).option);
	        b3.setTag(labels.get(2));
	        b3.setOnClickListener(clickListener);
	        
	        b4.setText(labels.get(3).option);
	        b4.setTag(labels.get(3));
	        b4.setOnClickListener(clickListener);
	        	
	}
	
    finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
    	mDbHelper.close();
    }
	}

	private void InicirajVariable() {
		Typeface pitanje = Typeface.createFromAsset(getAssets(), "myriad.ttf");
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
		
		question = (TextView) findViewById(R.id.tvPitanjeCigle);
		b1 = (Button) findViewById(R.id.bOdgCigle1);
		b2 = (Button) findViewById(R.id.bOdgCigle2);
		b3 = (Button) findViewById(R.id.bOdgCigle3);
		b4 = (Button) findViewById(R.id.bOdgCigle4);
		
		b1.setTypeface(dugmad);
		b2.setTypeface(dugmad);
		b3.setTypeface(dugmad);
		b4.setTypeface(dugmad);
		question.setTypeface(pitanje);
		
	}

	@Override
	public void onBackPressed() {
		//do nothing
	}
	
}
