package com.example.bunnyqueen.onlinebustickets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddPayPalActivity extends AppCompatActivity {

    Button addPaypalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay_pal);

        addPaypalButton = (Button)findViewById(R.id.addpaypalbtn);
        addPaypalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText email = findViewById(R.id.email);
                //email.getText().toString();

                EditText pass = findViewById(R.id.password);
               // pass.getText().toString();

                String FILENAME = "paypal_credentials";

                try {
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(email.getText().toString().getBytes());
                    fos.write(' ');
                    fos.write(pass.getText().toString().getBytes());
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

                startActivity(new Intent(AddPayPalActivity.this, HomeActivity.class));
            }
        });
    }

}
