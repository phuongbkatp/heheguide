package com.haris.navigato.ConnectionUtil;

/**
 * Created by hp on 5/20/2018.
 */

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.Utility.Utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connection {


    /**
     * <p>It send the request to Server & get Response</p>
     *
     * @param uri  Url of Server
     * @param json data in the form of Json
     * @param type type of request
     * @return response of server
     */
    public static String makeRequest(String uri, String json, String type) {
        HttpURLConnection urlConnection;
        String url;
        String data = json;
        String result = null;
        final int CONN_WAIT_TIME = 3000;
        final int CONN_DATA_WAIT_TIME = 2000;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(false);

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "text/html");
            urlConnection.setRequestMethod(type);
            urlConnection.setConnectTimeout(1000 * 20);
            urlConnection.connect();


            //Write

            Utility.Logger("MK Request 2", type);
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();


            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            Utility.Logger("Result of Servuice", result + " ");
            Utility.Logger("MK Request", type + " " + urlConnection.getResponseCode() + " " + uri + " " + json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Constant.ImportantMessages.CONNECTION_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return Constant.ImportantMessages.CONNECTION_ERROR;
        } finally {

        }
        return result;
    }


    /**
     * <p>It send the request to Server & get Response</p>
     *
     * @param uri  Url of Server
     * @param type type of request
     * @return response of server
     */
    public static String makeRequest(String uri, String type) {
        HttpURLConnection urlConnection;
        String url;

        String result = null;
        final int CONN_WAIT_TIME = 3000;
        final int CONN_DATA_WAIT_TIME = 2000;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(false);

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "text/html");
            urlConnection.setRequestMethod(type);
            urlConnection.setConnectTimeout(1000 * 20);
            urlConnection.connect();


            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

            Utility.Logger("Result of Servuice", result + " ");
            Utility.Logger("MK Request", type + " " + urlConnection.getResponseCode() + " " + uri);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Constant.ImportantMessages.CONNECTION_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return Constant.ImportantMessages.CONNECTION_ERROR;
        } finally {

        }
        return result;
    }


}
