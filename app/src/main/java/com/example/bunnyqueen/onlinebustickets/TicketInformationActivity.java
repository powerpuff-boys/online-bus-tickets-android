package com.example.bunnyqueen.onlinebustickets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TicketInformationActivity extends AppCompatActivity {

//    Button ticketInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        Bundle b;


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
