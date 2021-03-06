package rs.androidaplikacije.zastaveigradovi;

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAdapter 
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context) 
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException 
    {
        try 
        {
            mDbHelper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException 
    {
        try 
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } 
        catch (SQLException mSQLException) 
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() 
    {
        mDbHelper.close();
    }

     public Cursor getTestData(String whereClause)
     {;
         try
         {
             String sql ="SELECT * FROM tblPitanja WHERE 1 = 1 " + whereClause + " ORDER BY RANDOM() LIMIT 1";

             Cursor mCur = mDb.rawQuery(sql, null);
             if (mCur!=null)
             {
                mCur.moveToNext();
             }
             return mCur;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestData >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     public Cursor getTestDataGradovi(long id)
     {;
         try
         {
             String sql ="SELECT * FROM tblGradovi WHERE _ID = " + id;

             Cursor c = mDb.rawQuery(sql, null);
             if (c!=null)
             {
                c.moveToNext();
             }
             return c;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestDataGradovi >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
     public Cursor getTestDataValute(long id)
     {;
         try
         {
             String sql ="SELECT * FROM tblValute WHERE _ID = " + id;

             Cursor c = mDb.rawQuery(sql, null);
             if (c!=null)
             {
                c.moveToNext();
             }
             return c;
         }
         catch (SQLException mSQLException) 
         {
             Log.e(TAG, "getTestDataValute >>"+ mSQLException.toString());
             throw mSQLException;
         }
     }
}
