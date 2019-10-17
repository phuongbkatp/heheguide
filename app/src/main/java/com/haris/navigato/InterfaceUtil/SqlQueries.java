package com.haris.navigato.InterfaceUtil;

/**
 * Created by hp on 5/31/2018.
 */

public interface SqlQueries {

    /**
     * <p>Give query of SQL which can retrieve all data from Database</p>
     *
     * @return give SQl Query for retreiving all data
     */
    String retrieving();

    /**
     * <p>Give query of SQL which can update data in Database</p>
     *
     * @return give SQl Query for Updating data
     */
    String update();

    /**
     * <p>Give query of SQL which can insert data in Database</p>
     *
     * @return give SQl Query for inserting  data
     */
    String insert();

    /**
     * <p>Give query of SQL which can delete  data from Database</p>
     *
     * @return give SQl Query for delete data
     */
    String delete();


    /**
     * <p>Get the query for retrieving single id data</p>
     *
     * @return query for retrieving single id data
     */
    String retrieveSingleItem();

    /**
     * <p>Get the query for searching specific data</p>
     *
     * @return query of search data
     */
    String searchFromData();


    /**
     * <p>Getting Query for retrieving data between two dates</p>
     *
     * @return query for getting data between two dates
     */
    String dataBetweenTwoDate();

    /**
     * <p>Getting Query for retrieving reminder specific data either Reminder or Note</p>
     *
     * @return query for getting data of specific reminder Type
     */
    String retrieveSpecificReminderType();


    /**
     * <p>Getting Query for retrieving specific date data</p>
     *
     * @return query for getting data of specific date
     */
    String retrieveSpecificData();


    /**
     * <p>Getting Query for last Inserted Row Id</p>
     *
     * @return query
     */
    String getLAstInsertedId();


    /**
     * <p>Getting Query for retrieving Favourites item</p>
     *
     * @return query
     */
    String retrieveFavourite();

}




