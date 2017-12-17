package com.example.bunnyqueen.onlinebustickets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import http.Ticket;
import http.TicketHttpClient;
import http.Utils;

public class PayPalPaymentActivity extends AppCompatActivity {

    Button yesbtn;
    Button nobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment);


        yesbtn = (Button) findViewById(R.id.yes);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Read from file paypal credentials
                String credentials = "";
                try (FileInputStream fis = openFileInput("paypal_credentials")) {
                    int size;
                    while ((size = fis.read()) != -1) {
                        credentials += Character.toString((char) size);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] values = credentials.split(":");
                final String email = values[0];
                final String password = values[1];
                //End

                String busId = "";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    busId = extras.getString("busId");
                }

                //Write to file busId,startDate,EndDate
//                try {
//                    FileOutputStream fos = openFileOutput("bus_information", Context.MODE_PRIVATE);
//                    fos.write(busId.toString().getBytes());
//                    fos.write(':');
//                    //NOT FINISHED
//                    fos.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }

                Ticket ticket = null;
                TicketHttpClient thc = null;
                Ticket createdTicket = null;
                try {
                    thc = new TicketHttpClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String date = Utils.simpleDateFormat.format(Calendar.getInstance().getTime());
                    final Date dateCreated = Utils.simpleDateFormat.parse(date);
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    final String finalBusId = busId;
                    final TicketHttpClient finalThc = thc;
                    Callable<Ticket> task = new Callable<Ticket>() {
                        @Override
                        public Ticket call() throws Exception {
                            return finalThc.createTicket(new Ticket(finalBusId, dateCreated, false), email, password);
                        }
                    };

                   Future<Ticket> result =service.submit(task);
                    createdTicket = result.get();


                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //Send to server
                Intent i = new Intent(PayPalPaymentActivity.this,HomeActivity.class);
                i.putExtra("ticketId", createdTicket.getId());
                i.putExtra("expiration", createdTicket.getExpiresOn());
                startActivity(i);
            }
        });

        nobtn = (Button) findViewById(R.id.no);
        nobtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PayPalPaymentActivity.this, "You suck boiii!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PayPalPaymentActivity.this, TicketInformationActivity.class));
            }
        });
    }
}
