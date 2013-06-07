package rs.androidaplikacije.toplo_hladno;

import java.io.ByteArrayInputStream;
import java.text.Normalizer;
import java.util.LinkedList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Cigle extends Activity implements OnClickListener{
	
	boolean music;
	ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, 
	b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36;
	Button izlaz, konacnoResenje, rezultat, vreme;
	String konResenje, odgovorNormalized, opis;
	ImageView slika;
	int counter;
	public static int brojPoenaCigle = 0;
	public static int trenutnaIgra;
	public static int prvaIgra = 0;
	Editable ukucanRezultat;
	final Context context = this;
	boolean primljenoTacno;
	boolean primljenoPogresno;
	MediaPlayer buttonClicks, buttonBack, buttonFinal;
	String istekloVreme = "Isteklo vreme!!";
	String tacanOdgovor = "Tačan odgovor!!";

	
	MyCount brojacVremena = new MyCount(20000, 1000);	

	final private static int B1 = 1;
	final private static int B2 = 2;
	final private static int B3 = 3;
	final private static int B4 = 4;
	final private static int B5 = 5;
	final private static int B6 = 6;
	final private static int B7 = 7;
	final private static int B8 = 8;
	final private static int B9 = 9;
	final private static int B10 = 10;
	final private static int B11 = 11;
	final private static int B12 = 12;
	final private static int B13 = 13;
	final private static int B14 = 14;
	final private static int B15 = 15;
	final private static int B16 = 16;
	final private static int B17 = 17;
	final private static int B18 = 18;
	final private static int B19 = 19;
	final private static int B20 = 20;
	final private static int B21 = 21;
	final private static int B22 = 22;
	final private static int B23 = 23;
	final private static int B24 = 24;
	final private static int B25 = 25;
	final private static int B26 = 26;
	final private static int B27 = 27;
	final private static int B28 = 28;
	final private static int B29 = 29;
	final private static int B30 = 30;
	final private static int B31 = 31;
	final private static int B32 = 32;
	final private static int B33 = 33;
	final private static int B34 = 34;
	final private static int B35 = 35;
	final private static int B36 = 36;
	final private static int REQ = 37;
	
	LinkedList<Long> mAnsweredQuestions = new LinkedList<Long>();
	
	private String generateWhereClause(){
	    StringBuilder result = new StringBuilder();
	    for (Long l : mAnsweredQuestions){
	    	result.append(" AND _ID <> " + l);
	    }
	    return result.toString();
	}
	
	Handler mHandler = new Handler();
	
	Runnable mLaunchTaskFinish = new Runnable() {
        public void run() {
        	nextQuestion();
        }
     };

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent i) {
		super.onActivityResult(reqCode, resultCode, i);
		switch(reqCode) {
		case B1:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b1.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B2:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b2.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B3:
				primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
				if(primljenoTacno){
				brojPoenaCigle = brojPoenaCigle + 2;
				b3.setVisibility(View.INVISIBLE);
				rezultat.setText("" + brojPoenaCigle);
				}else{
					brojPoenaCigle = brojPoenaCigle + 0;
					rezultat.setText("" + brojPoenaCigle);
				}
				break;
		case B4:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b4.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B5:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b5.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B6:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b6.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B7:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b7.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B8:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b8.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B9:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b9.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B10:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b10.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B11:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b11.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B12:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b12.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B13:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b13.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B14:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b14.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B15:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b15.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B16:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b16.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B17:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b17.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B18:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b18.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B19:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b19.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B20:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b20.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B21:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b21.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B22:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b22.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B23:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b23.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B24:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b24.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B25:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b25.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B26:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b26.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B27:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b27.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B28:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b28.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B29:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b29.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B30:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b30.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B31:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b31.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B32:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b32.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B33:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b33.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B34:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b34.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B35:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b35.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case B36:
			primljenoTacno = i.getBooleanExtra("score", primljenoTacno);
			if(primljenoTacno){
			brojPoenaCigle = brojPoenaCigle + 2;
			b36.setVisibility(View.INVISIBLE);
			rezultat.setText("" + brojPoenaCigle);
			}else{
				brojPoenaCigle = brojPoenaCigle + 0;
				rezultat.setText("" + brojPoenaCigle);
			}
			break;
		case REQ:
		skloniCigle();
		rezultat.setText("" + brojPoenaCigle);
		mHandler.postDelayed(mLaunchTaskFinish,4000);
		break;
	}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.cigle);
		
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
        	trenutnaIgra = brojPoenaCigle - prvaIgra;
    		
    		if(counter<2){
    			prvaIgra = prvaIgra + brojPoenaCigle;
    		}
    		
    		Intent i = new Intent(Cigle.this, Popup_opis.class);
    		i.putExtra("brojPoenaPrimljeno", trenutnaIgra);
    		i.putExtra("poslatOpis", opis);
    		i.putExtra("naslov", istekloVreme);
    		i.putExtra("resenje", konResenje);
    		startActivityForResult(i, REQ);
    		
    		brojacVremena.cancel();
    		
    		konacnoResenje.setText(konResenje);
    		
        	}

		@Override
		public void onTick(long millisUntilFinished) {
			vreme.setText("" + millisUntilFinished / 1000);
			
		}
        }
	
	private void skloniCigle(){
		b1.setVisibility(View.INVISIBLE);
		b2.setVisibility(View.INVISIBLE);
		b3.setVisibility(View.INVISIBLE);
		b4.setVisibility(View.INVISIBLE);
		b5.setVisibility(View.INVISIBLE);
		b6.setVisibility(View.INVISIBLE);
		b7.setVisibility(View.INVISIBLE);
		b8.setVisibility(View.INVISIBLE);
		b9.setVisibility(View.INVISIBLE);
		b10.setVisibility(View.INVISIBLE);
		b11.setVisibility(View.INVISIBLE);
		b12.setVisibility(View.INVISIBLE);
		b13.setVisibility(View.INVISIBLE);
		b14.setVisibility(View.INVISIBLE);
		b15.setVisibility(View.INVISIBLE);
		b16.setVisibility(View.INVISIBLE);
		b17.setVisibility(View.INVISIBLE);
		b18.setVisibility(View.INVISIBLE);
		b19.setVisibility(View.INVISIBLE);
		b20.setVisibility(View.INVISIBLE);
		b21.setVisibility(View.INVISIBLE);
		b22.setVisibility(View.INVISIBLE);
		b23.setVisibility(View.INVISIBLE);
		b24.setVisibility(View.INVISIBLE);
		b25.setVisibility(View.INVISIBLE);
		b26.setVisibility(View.INVISIBLE);
		b27.setVisibility(View.INVISIBLE);
		b28.setVisibility(View.INVISIBLE);
		b29.setVisibility(View.INVISIBLE);
		b30.setVisibility(View.INVISIBLE);
		b31.setVisibility(View.INVISIBLE);
		b32.setVisibility(View.INVISIBLE);
		b33.setVisibility(View.INVISIBLE);
		b34.setVisibility(View.INVISIBLE);
		b35.setVisibility(View.INVISIBLE);
		b36.setVisibility(View.INVISIBLE);
		
	}
	private void poredjajCigle(){
		
		b1.setVisibility(View.VISIBLE);
		b2.setVisibility(View.VISIBLE);
		b3.setVisibility(View.VISIBLE);
		b4.setVisibility(View.VISIBLE);
		b5.setVisibility(View.VISIBLE);
		b6.setVisibility(View.VISIBLE);
		b7.setVisibility(View.VISIBLE);
		b8.setVisibility(View.VISIBLE);
		b9.setVisibility(View.VISIBLE);
		b10.setVisibility(View.VISIBLE);
		b11.setVisibility(View.VISIBLE);
		b12.setVisibility(View.VISIBLE);
		b13.setVisibility(View.VISIBLE);
		b14.setVisibility(View.VISIBLE);
		b15.setVisibility(View.VISIBLE);
		b16.setVisibility(View.VISIBLE);
		b17.setVisibility(View.VISIBLE);
		b18.setVisibility(View.VISIBLE);
		b19.setVisibility(View.VISIBLE);
		b20.setVisibility(View.VISIBLE);
		b21.setVisibility(View.VISIBLE);
		b22.setVisibility(View.VISIBLE);
		b23.setVisibility(View.VISIBLE);
		b24.setVisibility(View.VISIBLE);
		b25.setVisibility(View.VISIBLE);
		b26.setVisibility(View.VISIBLE);
		b27.setVisibility(View.VISIBLE);
		b28.setVisibility(View.VISIBLE);
		b29.setVisibility(View.VISIBLE);
		b30.setVisibility(View.VISIBLE);
		b31.setVisibility(View.VISIBLE);
		b32.setVisibility(View.VISIBLE);
		b33.setVisibility(View.VISIBLE);
		b34.setVisibility(View.VISIBLE);
		b35.setVisibility(View.VISIBLE);
		b36.setVisibility(View.VISIBLE);

	}
	
	private void InicirajVariable() {

		b1 = (ImageButton) findViewById(R.id.ib1);
		b2 = (ImageButton) findViewById(R.id.ib2);
		b3 = (ImageButton) findViewById(R.id.ib3);
		b4 = (ImageButton) findViewById(R.id.ib4);
		b5 = (ImageButton) findViewById(R.id.ib5);
		b6 = (ImageButton) findViewById(R.id.ib6);
		b7 = (ImageButton) findViewById(R.id.ib7);
		b8 = (ImageButton) findViewById(R.id.ib8);
		b9 = (ImageButton) findViewById(R.id.ib9);
		b10 = (ImageButton) findViewById(R.id.ib10);
		b11 = (ImageButton) findViewById(R.id.ib11);
		b12 = (ImageButton) findViewById(R.id.ib12);
		b13 = (ImageButton) findViewById(R.id.ib13);
		b14 = (ImageButton) findViewById(R.id.ib14);
		b15 = (ImageButton) findViewById(R.id.ib15);
		b16 = (ImageButton) findViewById(R.id.ib16);
		b17 = (ImageButton) findViewById(R.id.ib17);
		b18 = (ImageButton) findViewById(R.id.ib18);
		b19 = (ImageButton) findViewById(R.id.ib19);
		b20 = (ImageButton) findViewById(R.id.ib20);
		b21 = (ImageButton) findViewById(R.id.ib21);
		b22 = (ImageButton) findViewById(R.id.ib22);
		b23 = (ImageButton) findViewById(R.id.ib23);
		b24 = (ImageButton) findViewById(R.id.ib24);
		b25 = (ImageButton) findViewById(R.id.ib25);
		b26 = (ImageButton) findViewById(R.id.ib26);
		b27 = (ImageButton) findViewById(R.id.ib27);
		b28 = (ImageButton) findViewById(R.id.ib28);
		b29 = (ImageButton) findViewById(R.id.ib29);
		b30 = (ImageButton) findViewById(R.id.ib30);
		b31 = (ImageButton) findViewById(R.id.ib31);
		b32 = (ImageButton) findViewById(R.id.ib32);
		b33 = (ImageButton) findViewById(R.id.ib33);
		b34 = (ImageButton) findViewById(R.id.ib34);
		b35 = (ImageButton) findViewById(R.id.ib35);
		b36 = (ImageButton) findViewById(R.id.ib36);
		izlaz = (Button) findViewById(R.id.bIzlazCigle);
		rezultat = (Button) findViewById(R.id.bCigleRezultat);
		konacnoResenje = (Button) findViewById(R.id.bKonacnoCigle);
		slika = (ImageView) findViewById(R.id.ivSlika);
		vreme = (Button) findViewById(R.id.bCigleVreme);
		
		buttonClicks = MediaPlayer.create(this, R.raw.click);
		buttonBack = MediaPlayer.create(this, R.raw.button31);
		buttonFinal = MediaPlayer.create(this, R.raw.button);
		
		konacnoResenje.setText("Konačno rešenje!");
		
		Typeface dugmad = Typeface.createFromAsset(getAssets(), "ARIALN.TTF");
		izlaz.setTypeface(dugmad);
		rezultat.setTypeface(dugmad);
		konacnoResenje.setTypeface(dugmad);
		vreme.setTypeface(dugmad);
		vreme.setEnabled(false);
		rezultat.setEnabled(false);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		b10.setOnClickListener(this);
		b11.setOnClickListener(this);
		b12.setOnClickListener(this);
		b13.setOnClickListener(this);
		b14.setOnClickListener(this);
		b15.setOnClickListener(this);
		b16.setOnClickListener(this);
		b17.setOnClickListener(this);
		b18.setOnClickListener(this);
		b19.setOnClickListener(this);
		b20.setOnClickListener(this);
		b21.setOnClickListener(this);
		b22.setOnClickListener(this);
		b23.setOnClickListener(this);
		b24.setOnClickListener(this);
		b25.setOnClickListener(this);
		b26.setOnClickListener(this);
		b27.setOnClickListener(this);
		b28.setOnClickListener(this);
		b29.setOnClickListener(this);
		b30.setOnClickListener(this);
		b31.setOnClickListener(this);
		b32.setOnClickListener(this);
		b33.setOnClickListener(this);
		b34.setOnClickListener(this);
		b35.setOnClickListener(this);
		b36.setOnClickListener(this);
		izlaz.setOnClickListener(this);
		konacnoResenje.setOnClickListener(this);
		rezultat.setOnClickListener(this);
	}

	private void nextQuestion() {
		counter++;
		
		brojacVremena.start();  //startuje brojac vremena
		
        if(counter<3){
        	poredjajCigle();
        	konacnoResenje.setText("Konačno rešenje!");
        	
        	TestAdapter mDbHelper = new TestAdapter(this);
    		DataBaseHelper myDbHelper = new DataBaseHelper(this);
    		
    		if(!myDbHelper.checkDataBase()){
    		mDbHelper.createDatabase();
    		}
		
		try{    //Pokusava da otvori db
        	
	        mDbHelper.open();  //baza otvorena
	        
	        Cursor c = mDbHelper.getCigle(generateWhereClause());
	        
	        mAnsweredQuestions.add(c.getLong(0));	        

	        byte[] image_bytes = c.getBlob(c.getColumnIndex("PITANJE"));
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(image_bytes); 
	        Bitmap bp=BitmapFactory.decodeStream(inputStream);
	        
	        slika.setImageBitmap(bp);
	        konResenje = c.getString(2).toUpperCase();
	        opis = c.getString(3);
	        
	        odgovorNormalized = Normalizer.normalize(konResenje, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	        
		
		}
	        finally{    // kada zavrsi sa koriscenjem baze podataka, zatvara db
	        	mDbHelper.close();
	        }
        }else{
        	Intent resp = new Intent();
        	resp.putExtra("score", brojPoenaCigle);
        	setResult(1, resp);
			finish();
			brojacVremena.cancel();
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.ib1:
			if(music == true){
				buttonFinal.start();
					}
			Intent i = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i, B1);
			break;
		case R.id.ib2:
			if(music == true){
				buttonFinal.start();
					}
			Intent i2 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i2, B2);
			break;
		case R.id.ib3:
			if(music == true){
				buttonFinal.start();
					}
			Intent i3 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i3, B3);
			break;
		case R.id.ib4:
			if(music == true){
				buttonFinal.start();
					}
			Intent i4 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i4, B4);
			break;
		case R.id.ib5:
			if(music == true){
				buttonFinal.start();
					}
			Intent i5 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i5, B5);
			break;
		case R.id.ib6:
			if(music == true){
				buttonFinal.start();
					}
			Intent i6 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i6, B6);
			break;
		case R.id.ib7:
			if(music == true){
				buttonFinal.start();
					}
			Intent i7 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i7, B7);
			break;
		case R.id.ib8:
			if(music == true){
				buttonFinal.start();
					}
			Intent i8 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i8, B8);
			break;
		case R.id.ib9:
			if(music == true){
				buttonFinal.start();
					}
			Intent i9 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i9, B9);
			break;
		case R.id.ib10:
			if(music == true){
				buttonFinal.start();
					}
			Intent i10 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i10, B10);
			break;
		case R.id.ib11:
			if(music == true){
				buttonFinal.start();
					}
			Intent i11 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i11, B11);
			break;
		case R.id.ib12:
			if(music == true){
				buttonFinal.start();
					}
			Intent i12 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i12, B12);
			break;
		case R.id.ib13:
			if(music == true){
				buttonFinal.start();
					}
			Intent i13 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i13, B13);
			break;
		case R.id.ib14:
			if(music == true){
				buttonFinal.start();
					}
			Intent i14 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i14, B14);
			break;
		case R.id.ib15:
			if(music == true){
				buttonFinal.start();
					}
			Intent i15 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i15, B15);
			break;
		case R.id.ib16:
			if(music == true){
				buttonFinal.start();
					}
			Intent i16 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i16, B16);
			break;
		case R.id.ib17:
			if(music == true){
				buttonFinal.start();
					}
			Intent i17 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i17, B17);
			break;
		case R.id.ib18:
			if(music == true){
				buttonFinal.start();
					}
			Intent i18 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i18, B18);
			break;
		case R.id.ib19:
			if(music == true){
				buttonFinal.start();
					}
			Intent i19 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i19, B19);
			break;
		case R.id.ib20:
			if(music == true){
				buttonFinal.start();
					}
			Intent i20 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i20, B20);
			break;
		case R.id.ib21:
			if(music == true){
				buttonFinal.start();
					}
			Intent i21 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i21, B21);
			break;
		case R.id.ib22:
			if(music == true){
				buttonFinal.start();
					}
			Intent i22 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i22, B22);
			break;
		case R.id.ib23:
			if(music == true){
				buttonFinal.start();
					}
			Intent i23 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i23, B23);
			break;
		case R.id.ib24:
			if(music == true){
				buttonFinal.start();
					}
			Intent i24 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i24, B24);
			break;
		case R.id.ib25:
			if(music == true){
				buttonFinal.start();
					}
			Intent i25 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i25, B25);
			break;
		case R.id.ib26:
			if(music == true){
				buttonFinal.start();
					}
			Intent i26 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i26, B26);
			break;
		case R.id.ib27:
			if(music == true){
				buttonFinal.start();
					}
			Intent i27 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i27, B27);
			break;
		case R.id.ib28:
			if(music == true){
				buttonFinal.start();
					}
			Intent i28 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i28, B28);
			break;
		case R.id.ib29:
			if(music == true){
				buttonFinal.start();
					}
			Intent i29 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i29, B29);
			break;
		case R.id.ib30:
			if(music == true){
				buttonFinal.start();
					}
			Intent i30 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i30, B30);
			break;
		case R.id.ib31:
			if(music == true){
				buttonFinal.start();
					}
			Intent i31 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i31, B31);
			break;
		case R.id.ib32:
			if(music == true){
				buttonFinal.start();
					}
			Intent i32 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i32, B32);
			break;
		case R.id.ib33:
			if(music == true){
				buttonFinal.start();
					}
			Intent i33 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i33, B33);
			break;
		case R.id.ib34:
			if(music == true){
				buttonFinal.start();
					}
			Intent i34 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i34, B34);
			break;
		case R.id.ib35:
			if(music == true){
				buttonFinal.start();
					}
			Intent i35 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i35, B35);
			break;
		case R.id.ib36:
			if(music == true){
				buttonFinal.start();
					}
			Intent i36 = new Intent(Cigle.this, Pitanja_Cigle.class);
			startActivityForResult(i36, B36);
			break;
		case R.id.bIzlazCigle:
			if(music == true){
				buttonBack.start();
					}
			Intent resp = new Intent();
        	resp.putExtra("score", brojPoenaCigle);
        	setResult(1, resp);
        	brojacVremena.cancel();
        	finish();
			break;
		case R.id.bKonacnoCigle:
			if(music == true){
				buttonClicks.start();
					}
			LayoutInflater layoutInflater = LayoutInflater.from(context);	
            View promptView = layoutInflater.inflate(R.layout.popup_answer, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            // set prompts.xml to be the layout file of the alertdialog builder
            alertDialogBuilder.setView(promptView);
            final EditText input = (EditText) promptView.findViewById(R.id.userInput);
            
            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    
                                                	ukucanRezultat = input.getText();
                                                	String ukucanRezultatStr = ukucanRezultat.toString().toUpperCase();;                  	
                                                	
                                                	if (ukucanRezultatStr.equals(odgovorNormalized)){
                                                		konacnoResenje.setText(konResenje);
                                                		
                                                		brojPoenaCigle = brojPoenaCigle + 10;
                                                		
                                                		trenutnaIgra = brojPoenaCigle - prvaIgra;
                                                		
                                                		if(counter<2){
                                                			prvaIgra = prvaIgra + brojPoenaCigle;
                                                		}

                                                		Intent i = new Intent(Cigle.this, Popup_opis.class);
                                                		i.putExtra("brojPoenaPrimljeno", trenutnaIgra);
                                                		i.putExtra("poslatOpis", opis);
                                                		i.putExtra("naslov", tacanOdgovor);
                                                		i.putExtra("resenje", konResenje);
                                                		startActivityForResult(i, REQ);
                                                		
                                                		brojacVremena.cancel();
                                            			
                                            			
                                                 }else{
                                                  	Toast.makeText(Cigle.this, "Pogresan odgovor!", Toast.LENGTH_SHORT).show();
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

                            AlertDialog alertD = alertDialogBuilder.create();
                            alertD.show();
           
                            break;
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//skloniCigle();	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	        brojacVremena.cancel();
	    }
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
		brojacVremena.cancel();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		brojacVremena.cancel();
		finish();
	}
	
}
