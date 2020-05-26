package com.kjb.ocs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class JSONTask_ extends AsyncTask<String,String,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("e_mail",urls[1]);
            jsonObject.accumulate("pw",urls[2]);
            jsonObject.accumulate("name",urls[3]);
            jsonObject.accumulate("grade",urls[4]);
            jsonObject.accumulate("classNum",urls[5]);
            jsonObject.accumulate("number",urls[6]);
            jsonObject.accumulate("major",urls[7]);

            HttpURLConnection con = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(urls[0]);
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Cache-Control","no-cache");
                con.setRequestProperty("Content-Type","application/json");
                con.setRequestProperty("Accept","text/html");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.connect();

                OutputStream outStream = con.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();

                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line ="";
                while ((line = reader.readLine())!= null){
                    buffer.append(line);
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(con!=null){
                    con.disconnect();
                }
                try {
                    if(reader !=null){
                        reader.close();
                    }
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecute: Success");
    }



}