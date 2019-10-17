package com.haris.navigato.DatabaseUtil;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.SqlQueries;
import com.haris.navigato.Utility.Utility;

/**
 * Created by hp on 6/1/2018.
 */

public class HistoryDbQueries implements SqlQueries {


    public HistoryDbQueries() {

        Utility.Logger(Constant.DatabaseColumn.TAG, "Working");

    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.HISTORY_TABLE_NAME;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.HISTORY_TABLE_NAME + "(" + Constant.DatabaseColumn.ROUTE_COLUMN + "," + Constant.DatabaseColumn.BY_COLUMN + "," + Constant.DatabaseColumn.SOURCE_NAME_COLUMN + "," + Constant.DatabaseColumn.DESTINATION_NAME_COLUMN + ","
                + Constant.DatabaseColumn.DISTANCE_COLUMN + "," + Constant.DatabaseColumn.DURATION_COLUMN + "," + Constant.DatabaseColumn.PETROL_COLUMN +
                "," + Constant.DatabaseColumn.SOURCE_LATITUDE_COLUMN + "," + Constant.DatabaseColumn.SOURCE_LONGITUDE_COLUMN + "," + Constant.DatabaseColumn.DESTINATION_LATITUDE_COLUMN + "," + Constant.DatabaseColumn.DESTINATION_LONGITUDE_COLUMN + ") VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)";

    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.HISTORY_TABLE_NAME + " WHERE " + Constant.DatabaseColumn.HISTORY_ID_COLUMN + "=" + "%s";
    }

    @Override
    public String retrieveSingleItem() {
        return "SELECT * FROM " + Constant.DatabaseColumn.HISTORY_TABLE_NAME + " WHERE " + Constant.DatabaseColumn.HISTORY_ID_COLUMN + " = " + "%s";
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
