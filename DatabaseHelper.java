package rs.androidaplikacije.zastaveigradovi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
//destination path (location) of our database on device
private static String DB_PATH = "/data/data/rs.androidaplikacije.flags/databases/";
private static String DB_NAME ="pitanja.sqlite";// Database name
private static SQLiteDatabase mDataBase; 
private final Context mContext;
private static final int DATABASE_VERSION = 1;

public DataBaseHelper(Context mojContext) 
{
    super(mojContext, DB_NAME, null, 1);// 1? its Database Version
    DB_PATH = mojContext.getApplicationInfo().dataDir + "/databases/";
    this.mContext = mojContext;
}

public void createDataBase() throws IOException
{
    //If database not exists copy it from the assets

   
        this.getReadableDatabase();
        this.close();
        try 
        {
            //Copy the database from assests
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        } 
        catch (IOException mIOException) 
        {
            throw new Error("ErrorCopyingDataBase");
        }
    }
/**
 * Check if the database already exist to avoid re-copying the file each time you open the application.
 *  true if it exists, false if it doesn't
 */
public boolean checkDataBase(){

SQLiteDatabase checkDB = null;

try{
String myPath = DB_PATH + DB_NAME;
checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

}catch(SQLiteException e){

//database does't exist yet.

}

if(checkDB != null){

checkDB.close();

}

return checkDB != null ? true : false;
}
    /*Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }
    */

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }
    
    public void close() 
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

  
	public void onCreate(SQLiteDatabase arg0) {
		}

	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 mContext.deleteDatabase(DB_NAME);
		 try {
			copyDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
