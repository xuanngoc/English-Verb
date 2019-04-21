package com.example.verbs.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class VerbDb {

    protected static final String TAG = "VerbDb";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private VerbDbHelper mDbHelper;

    public VerbDb(Context context)
    {
        this.mContext = context;
        mDbHelper = new VerbDbHelper(mContext);
    }

    public VerbDb createDatabase() throws SQLException
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

    public VerbDb open() throws SQLException
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

    public Cursor getTestData()
    {
        try
        {
            /*String[] column = {
                Verb._id,
                Verb.INFINITIVE,
                Verb.SIMPLE_PAST,
                Verb.PAST_PARTICIPLE,
                Verb.PHONETIC_INFINITIVE,
                Verb.PHONETIC_SIMPLE_PAST,
                Verb.PHONETIC_PAST_PARTICIPLE,
                Verb.DEFINITION,
                Verb.SAMPLE1,
                Verb.SAMPLE2,
                Verb.SAMPLE3
            };

            Cursor mCur = mDb.query(Verb.TABLE_NAME, column, null, null, null, null, null );
            // = mDb.rawQuery(sql, null);*/

            String sql = "SELECT * FROM VERBS_TBL";
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

    public Cursor getResultSearch(String query) {
        try {
            String sql = "SELECT " + Verb._id + ", "
                    + Verb.INFINITIVE + ", "
                    + Verb.SIMPLE_PAST + ", "
                    + Verb.PAST_PARTICIPLE + ", "
                    + Verb.PHONETIC_INFINITIVE + ", "
                    + Verb.PHONETIC_SIMPLE_PAST + ", "
                    + Verb.PHONETIC_PAST_PARTICIPLE + ", "
                    + Verb.DEFINITION + ", "
                    + Verb.SAMPLE1 + ", "
                    + Verb.SAMPLE2 + ", "
                    + Verb.SAMPLE3 + " "
                    + " FROM " + Verb.TABLE_NAME
                    + " WHERE " + Verb.INFINITIVE + " LIKE \"%" +  query + "%\" " +
                    " OR " + Verb.SIMPLE_PAST + " LIKE \"%" +  query + "%\""  +
                    " OR " + Verb.PAST_PARTICIPLE + " LIKE \"%" +  query +"%\" ;" ;

            Log.d("DB", sql);
            Cursor mCur = mDb.rawQuery(sql, null);
            // = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());

        }
        return null;
    }

    public Cursor getDetailData()
    {
        try
        {
            String[] column = {
                    Verb._id,
                    Verb.INFINITIVE,
                    Verb.SIMPLE_PAST,
                    Verb.PAST_PARTICIPLE,
                    Verb.PHONETIC_INFINITIVE,
                    Verb.PHONETIC_SIMPLE_PAST,
                    Verb.PHONETIC_PAST_PARTICIPLE,
                    Verb.DEFINITION,
                    Verb.SAMPLE1,
                    Verb.SAMPLE2,
                    Verb.SAMPLE3
            };

            Cursor mCur = mDb.query(Verb.TABLE_NAME, column, null, null, null, null, null );
            // = mDb.rawQuery(sql, null);
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
}
