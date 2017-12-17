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

import java.util.Calendar;


public class TicketInformationActivity extends AppCompatActivity {

    ImageView image;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        String ticketId = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ticketId = extras.getString("ticketInfo");
        }

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

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        startDate = (TextView) findViewById(R.id.date_time_start);

        StringBuilder start = new StringBuilder();
        start.append("Bought on: ");
        start.append(today.monthDay).append("/").append(today.month + 1).append("/").append(today.year).append(" ");
        start.append(today.hour).append(":").append(today.minute);
        startDate.setText(start.toString());

        endDate = (TextView) findViewById(R.id.date_time_end);

        StringBuilder end = new StringBuilder();
        end.append("Expires on: ");
        end.append(today.monthDay).append("/").append(today.month + 1).append("/").append(today.year).append(" ");
        end.append(today.hour + 2).append(":").append(today.minute);
        endDate.setText(end.toString());


//        String busInfo = "";
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            busInfo = extras.getString("busInfo");
//        }
//        String[] values = busInfo.split(" ");

//        ViewGroup linearLayout = (ViewGroup) findViewById(R.id.linLayout);
//        Button button = new Button(this);
//        button.setText("Ticket 1");
//        button.setTextSize(22);
//        button.setGravity(Gravity.CENTER);
//
//        linearLayout.addView(button);
//
//        this.setContentView(linearLayout, new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        TextView busInfoDisplay = (TextView) findViewById(R.id.busInfo);
//        busInfoDisplay.setText(busInfo);
    }
}
