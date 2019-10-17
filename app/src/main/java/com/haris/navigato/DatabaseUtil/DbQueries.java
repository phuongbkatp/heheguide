package com.haris.navigato.DatabaseUtil;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.SqlQueries;
import com.haris.navigato.Utility.Utility;

/**
 * Created by hp on 5/31/2018.
 */

public class DbQueries implements SqlQueries {


    public DbQueries() {

        Utility.Logger(Constant.DatabaseColumn.TAG, "Working");

    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.TABLE_NAME;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.TABLE_NAME + "(" + Constant.DatabaseColumn.PLACE_PICTURE + "," + Constant.DatabaseColumn.PLACE_ID + "," + Constant.DatabaseColumn.PLACE_NAME + "," + Constant.DatabaseColumn.PRICE_LEVEL + ","
                + Constant.DatabaseColumn.PLACE_RATING + "," + Constant.DatabaseColumn.PLACE_TYPE + "," + Constant.DatabaseColumn.PLACE_ADDRESS +
                "," + Constant.DatabaseColumn.PLACE_LATITUDE + "," + Constant.DatabaseColumn.PLACE_LONGITUDE + ") VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)";

    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.TABLE_NAME + " WHERE " + Constant.DatabaseColumn.ID_COLUMN + "=" + "%s";
    }

    @Override
    public String retrieveSingleItem() {
        return "SELECT * FROM " + Constant.DatabaseColumn.TABLE_NAME + " WHERE " + Constant.DatabaseColumn.PLACE_ID + " = " + "%s";
    }

    @Override
    public String searchFromData() {
        return null;
    }

    @Override
    public String dataBetweenTwoDate() {
        return null;
    }

    @Override
    public String retrieveSpecificReminderType() {
        return null;
    }

    @Override
    public String retrieveSpecificData() {
        return null;
    }

    @Override
    public String getLAstInsertedId() {
        return null;
    }

    @Override
    public String retrieveFavourite() {
        return null;
    }
}
