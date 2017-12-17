package com.example.bunnyqueen.onlinebustickets;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import http.HttpUtil;
import http.Utils;


public class TicketInformationActivity extends AppCompatActivity {

    ImageView image;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        String ticket_info = "";
        try (FileInputStream fis = openFileInput("bus_information")) {
            int size;
            while ((size = fis.read()) != -1) {
                ticket_info += Character.toString((char) size);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] values = ticket_info.split("@");
        final String ticketId = values[0];
        final String createdOn = values[1];
        final String expiresOn = values[2];

//        String ticketId = "";
//        String expiration = "";
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            ticketId = extras.getString("ticketId");
//            expiration = extras.getString("expiration");
//        }

        image = (ImageView) findViewById(R.id.image);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(ticketId, BarcodeFormat.QR_CODE, 480, 480);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        } catch (WriterException we) {
            we.printStackTrace();
        }

        startDate = (TextView)findViewById(R.id.date_time_start);
        startDate.setText("Bought on: " + createdOn);

        endDate = (TextView)findViewById(R.id.date_time_end);
        endDate.setText("Expires on: " + expiresOn);


//        Time today = new Time(Time.getCurrentTimezone());
//        today.setToNow();

//        startDate = (TextView) findViewById(R.id.date_time_start);
//
//        StringBuilder start = new StringBuilder();
//        start.append("Bought on: ");
//        start.append(today.monthDay).append("/").append(today.month + 1).append("/").append(today.year).append(" ");
//        start.append(today.hour).append(":").append(today.minute);
//        startDate.setText(start.toString());
//
//        endDate = (TextView) findViewById(R.id.date_time_end);
//
//        StringBuilder end = new StringBuilder();
//        end.append("Expires on: ");
//        end.append(expiration);
//        endDate.setText(end.toString());

    }
}
