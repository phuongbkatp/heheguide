package com.haris.navigato.DatabaseUtil;

import android.database.DatabaseUtils;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.InterfaceUtil.SqlQueries;
import com.haris.navigato.ObjectUtil.DatabaseQueryObject;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;

/**
 * Created by hp on 5/31/2018.
 */

public class QueryFactory {


    /**
     * <p>It is used to get specific query</p>
     *
     * @param database_event
     * @return
     */
    public static String getQuery(Constant.DATABASE_EVENT database_event, Constant.DATABASE_FUNCTION database_function, DatabaseQueryObject databaseQueryObject, HistoryQueryObject historyQueryObject) {
        String query = null;
        SqlQueries dbQueries = null;

        if (database_function == Constant.DATABASE_FUNCTION.FAVOURITE) {
            dbQueries = new DbQueries();
        } else if (database_function == Constant.DATABASE_FUNCTION.HISTORY) {
            dbQueries = new HistoryDbQueries();
        }

        if (database_event == Constant.DATABASE_EVENT.RETRIEVE) {
            query = dbQueries.retrieving();
        } else if (database_event == Constant.DATABASE_EVENT.INSERT) {
            query = dbQueries.insert();
        } else if (database_event == Constant.DATABASE_EVENT.DELETE) {
            query = dbQueries.delete();
        } else if (database_event == Constant.DATABASE_EVENT.SINGLE) {
            query = dbQueries.retrieveSingleItem();
        }

        if (database_function == Constant.DATABASE_FUNCTION.FAVOURITE) {

            if (database_event == Constant.DATABASE_EVENT.DELETE) {

                query = String.format(query, DatabaseUtils.sqlEscapeString(historyQueryObject.getId()));

            } else if (database_event == Constant.DATABASE_EVENT.INSERT) {


                query = String.format(query, DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlacePicture()), DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceId()), DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceName())
                        , DatabaseUtils.sqlEscapeString(databaseQueryObject.getPriceLevel()), DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceRating()), DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceType()), DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceAddress())
                        , DatabaseUtils.sqlEscapeString(String.valueOf(databaseQueryObject.getPlaceLatitude())), DatabaseUtils.sqlEscapeString(String.valueOf(databaseQueryObject.getPlaceLongitude())));

            } else if (database_event == Constant.DATABASE_EVENT.SINGLE) {

                query = String.format(query, DatabaseUtils.sqlEscapeString(databaseQueryObject.getPlaceId()));

            }

        } else if (database_function == Constant.DATABASE_FUNCTION.HISTORY) {


            if (database_event == Constant.DATABASE_EVENT.DELETE) {

                query = String.format(query, DatabaseUtils.sqlEscapeString(historyQueryObject.getId()));

            } else if (database_event == Constant.DATABASE_EVENT.INSERT) {

                query = String.format(query, DatabaseUtils.sqlEscapeString(historyQueryObject.getRouteName()), DatabaseUtils.sqlEscapeString(historyQueryObject.getConveyanceName()), DatabaseUtils.sqlEscapeString(historyQueryObject.getSourceName())
                        , DatabaseUtils.sqlEscapeString(historyQueryObject.getDestinationName()), DatabaseUtils.sqlEscapeString(historyQueryObject.getDistance()), DatabaseUtils.sqlEscapeString(historyQueryObject.getDuration()), DatabaseUtils.sqlEscapeString(historyQueryObject.getPetrol())
                        , DatabaseUtils.sqlEscapeString(String.valueOf(historyQueryObject.getSourceLatitude())), DatabaseUtils.sqlEscapeString(String.valueOf(historyQueryObject.getSourceLongitude()))
                        , DatabaseUtils.sqlEscapeString(String.valueOf(historyQueryObject.getDestinationLatitude())), DatabaseUtils.sqlEscapeString(String.valueOf(historyQueryObject.getDestinationLongitude())));


            } else if (database_event == Constant.DATABASE_EVENT.SINGLE) {

                query = String.format(query, DatabaseUtils.sqlEscapeString(historyQueryObject.getId()));

            }

        }

        return query;
    }


}
