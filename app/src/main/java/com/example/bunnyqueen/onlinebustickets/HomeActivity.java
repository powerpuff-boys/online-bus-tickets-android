package com.example.bunnyqueen.onlinebustickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class HomeActivity extends AppCompatActivity {

    Button payPalButton;
    ImageButton imageCameraButton;
    ImageButton imageTicketsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        payPalButton = (Button) findViewById(R.id.button_paypal);
        payPalButton.setVisibility(View.INVISIBLE);

        boolean hasAddedPaypal = true;
        try(FileInputStream fis = openFileInput("paypal_credentials")) {
        } catch (FileNotFoundException e) {
            payPalButton.setVisibility(View.VISIBLE);
            hasAddedPaypal = false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!hasAddedPaypal) {
            payPalButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this, AddPayPalActivity.class));
                }
            });
        }

        imageCameraButton = (ImageButton) findViewById(R.id.image_button_camera);
        imageCameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Focus the camera to the QR Code.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, QR_CodeReaderActivity.class));
            }
        });

        imageTicketsButton = (ImageButton) findViewById(R.id.image_button_info);
        imageTicketsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TicketInformationActivity.class));
            }
        });
    }
}
