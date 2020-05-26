package com.kjb.ocs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class SignInActivity extends AppCompatActivity {

    Button btn_register;
    EditText id_email,id_pw,id_name;

    String result;

    JSONTask_ cr = new JSONTask_();
    NullEmpty ne = new NullEmpty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Spinner classSpinner = findViewById(R.id.id_class);
        Spinner classnumSpinner = findViewById(R.id.id_classnum);
        Spinner numSpinner = findViewById(R.id.id_num);
        Spinner majorSpinner = findViewById(R.id.major_array);
        btn_register = findViewById(R.id.button2);
        id_email = findViewById(R.id.id_email);
        id_pw = findViewById(R.id.id_pw);
        id_name = findViewById(R.id.id_name);
        final String[] data = new String[4];
        final String[] class_arr = getResources().getStringArray(R.array.id_class);
        final String[] classnum_arr = getResources().getStringArray(R.array.id_classnum);
        final String[] num_arr = getResources().getStringArray(R.array.id_num);
        final String[] major_arr = getResources().getStringArray(R.array.major);


        ArrayAdapter classAdapter = ArrayAdapter.createFromResource(this, R.array.id_class,android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        ArrayAdapter classnumAdapter = ArrayAdapter.createFromResource(this, R.array.id_classnum, android.R.layout.simple_spinner_item);
        classnumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classnumSpinner.setAdapter(classnumAdapter);

        ArrayAdapter numAdapter = ArrayAdapter.createFromResource(this, R.array.id_num, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numSpinner.setAdapter(numAdapter);

        ArrayAdapter majorAdapter = ArrayAdapter.createFromResource(this, R.array.major,android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);


        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data[0] = class_arr[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        classnumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data[1] = classnum_arr[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data[2] = num_arr[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data[3] = major_arr[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ne.NullEmpty(id_email.getText().toString(),id_pw.getText().toString(),id_name.getText().toString()) == "true") {
                    AsyncTask<String,String,String> at = cr.execute("http://10.120.73.44:8080/member/register",id_email.getText().toString(),id_pw.getText().toString(),id_name.getText().toString(),data[0],data[1],data[2],data[3]);

                try {
                    result = at.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject =  new JSONObject(result);
                    String status = jsonObject.getString("status");
                    int login_Dec = Integer.parseInt(status);
                    switch (login_Dec) {
                        case 200:
                            Toast.makeText(SignInActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 302:
                            Toast.makeText(SignInActivity.this, "이미 가입이 된 e_mail입니다!", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(SignInActivity.this, "양식 확인!", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(SignInActivity.this, "서버 에러!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
                else{
                    Toast.makeText(getApplicationContext(), "정보를 다시 확인해 주십시오", LENGTH_SHORT).show();
                }
            }
        });



    }

}

