package com.haris.navigato.DatabaseUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

/**
 * Created by hp on 5/31/2018.
 */

public class QueryRunner {
    ArrayList<Object> objectArrayList = new ArrayList<>();

    /**
     * <p>It contain methods for executing the SQL Database Queries & get Required Result</p>
     */
    public QueryRunner() {

    }


    /**
     * <p>It execute Query and return data in the form of list by adding into arraylist</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return arraylist which contain all of required data from Database
     */
    public ArrayList<Object> getAll(Constant.DATABASE_FUNCTION database_function, String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger("Query 1", query);
        Cursor cursor = getCursor(query, sqLiteOpenHelper);
        objectArrayList.clear();

        Utility.Logger("Size of Cursor", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {

                if (database_function == Constant.DATABASE_FUNCTION.FAVOURITE)
                    objectArrayList.add(new DatabaseQueryObject(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                            , cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), Double.parseDouble(cursor.getString(8)), Double.parseDouble(cursor.getString(9))));
                else {

                    Utility.Logger("History Id", cursor.getString(0));

                    objectArrayList.add(new HistoryQueryObject(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                            , cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), Double.parseDouble(cursor.getString(8)), Double.parseDouble(cursor.getString(9))
                            , Double.parseDouble(cursor.getString(10)), Double.parseDouble(cursor.getString(11))));

                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        return objectArrayList;
    }


    /**
     * <p>Execute query and give true/false on Success or Failing</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return true if perform successfully
     */
    public boolean getStatus(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Cursor cursor = getCursor(query, sqLiteOpenHelper);
        cursor.moveToFirst();
        /*Utility.Logger("Value ", String.valueOf(cursor.getLong(0)));*/
        cursor.close();
        return true;
    }


    /**
     * <p>Execute query and give last inserted Row Id</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return long      last inserted id
     */
    public long getLastInsertID(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        long lastInsertedId;
        Cursor cursor = getCursor(query, sqLiteOpenHelper);
        cursor.moveToFirst();
        lastInsertedId = cursor.getLong(0);
        cursor.close();
        return lastInsertedId;
    }


    /**
     * <p>It run the SQL Query and return data in Cursor</p>
     *
     * @param query            SQL Query which we want to run
     * @param sqLiteOpenHelper Instance of SQLiteOpenHelper
     * @return cursor which contain data fetch from Database
     */
    private Cursor getCursor(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger("Query", query);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ////db.execSQL(query);
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }


}

