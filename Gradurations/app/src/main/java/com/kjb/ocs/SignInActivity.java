package com.kjb.ocs;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Spinner classSpinner = findViewById(R.id.id_class);
        Spinner classnumSpinner = findViewById(R.id.id_classnum);
        Spinner numSpinner = findViewById(R.id.id_num);

        final ArrayAdapter classAdapter = ArrayAdapter.createFromResource(this, R.array.id_class,android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
        classSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SignInActivity.this,
                        (Integer) classAdapter.getItem(i),Toast.LENGTH_SHORT).show();
            }

        });

        ArrayAdapter classnumAdapter = ArrayAdapter.createFromResource(this,
                R.array.id_classnum, android.R.layout.simple_spinner_item);
        classnumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classnumSpinner.setAdapter(classnumAdapter);

        ArrayAdapter numAdapter = ArrayAdapter.createFromResource(this,
                R.array.id_num, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numSpinner.setAdapter(numAdapter);

    }

}

