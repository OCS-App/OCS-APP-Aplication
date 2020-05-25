package com.kjb.ocs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.dd.CircularProgressButton;

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

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    TextView tvdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        tvdata = findViewById(R.id.textView2);
        final EditText id_email = findViewById(R.id.id_email);
        final EditText id_pw = findViewById(R.id.id_pw);
        CircularProgressButton login_btn = findViewById(R.id.btnWithText);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://10.120.73.44:8080/member/login");
            }
        });
    }

    public class JSONTask extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("e_mail","test1@gsm.hs.kr");
                jsonObject.accumulate("pw","1234");

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

                } catch (MalformedURLException  e) {
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
            tvdata.setText(result);
        }
    }
}

