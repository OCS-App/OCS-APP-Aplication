package com.kjb.ocs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.dd.CircularProgressButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
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
import static android.content.ContentValues.TAG;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    TextView tvdata;
    EditText id_email;
    EditText id_pw;

    String result;
    JSONTask_ cr = new JSONTask_();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        tvdata = findViewById(R.id.textView2);
        id_email = findViewById(R.id.id_email);
        id_pw = findViewById(R.id.id_pw);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AsyncTask<String,String,String> at = cr.execute("http://10.120.73.44:8080/member/login",id_email.getText().toString(),id_pw.getText().toString(),null,null,null,null,null);
               try {
                    result = at.get();
               } catch (Exception e) {
                   e.printStackTrace();
               }

                try {
                    JSONObject jsonObject =  new JSONObject(result);
                    String status = jsonObject.getString("status");
                    tvdata.setText(status);
                    int login_Dec = Integer.parseInt(status);
                    switch (login_Dec) {
                        case 200:
                            tvdata.setText("로그인 성공");
                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 400:
                            tvdata.setText("아이디와 이메일을 확인해 주세요");
                            break;
                        case 401:
                            tvdata.setText("가입이 되어 있지 않은 아이디 입니다.");
                            break;
                        case 500:
                            tvdata.setText("서버 에러!");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

