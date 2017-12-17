package com.example.bunnyqueen.onlinebustickets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PayPalPaymentActivity extends AppCompatActivity {

    Button yesbtn;
    Button nobtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment);


        yesbtn = (Button)findViewById(R.id.yes);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                //Read from file paypal
                //Send to server
                //startActivity(new Intent(HomeActivity.this, QR_CodeReaderActivity.class));
            }
        });



        nobtn = (Button)findViewById(R.id.no);
        nobtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PayPalPaymentActivity.this, "You suck boiii!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PayPalPaymentActivity.this, TicketInformationActivity.class));
            }
        });
        //Read PayPal account from file



    }
}
