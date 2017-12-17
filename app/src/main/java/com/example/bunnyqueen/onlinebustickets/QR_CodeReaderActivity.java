package com.example.bunnyqueen.onlinebustickets;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;


import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import http.Ticket;
import http.TicketHttpClient;
import http.Utils;

public class QR_CodeReaderActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_codereader);

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QR_CodeReaderActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if (qrcodes.size() != 0) {
                    gotoNextActivity(qrcodes.valueAt(0).displayValue);
                }
//                Ticket ticket = null;
//                if (qrcodes.size() != 0) {
//                    TicketHttpClient thc = null;
//                    try {
//                        thc = new TicketHttpClient();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        // Read username and pass from file
//                        String busId = qrcodes.valueAt(0).displayValue;
//                        Date dateCreated = Utils.simpleDateFormat.parse("2017-12-17T02:37:20");
//                        ticket = thc.createTicket(new Ticket(busId, dateCreated,false),"encho.belezirev@sap.com","fmi-codes");
//                        //ticket.getId();
//                    } catch (IOException | ParseException e) {
//                        e.printStackTrace();
//                    }
//                    gotoNextActivity();
//                }
            }
        });
    }

    public void gotoNextActivity() {
        Intent i = new Intent(QR_CodeReaderActivity.this, PayPalPaymentActivity.class);
        startActivity(i);
    }

    public void gotoNextActivity(String busId) {
        Intent i = new Intent(QR_CodeReaderActivity.this, PayPalPaymentActivity.class);
        i.putExtra("busId",busId);
        startActivity(i);
    }

//    public void gotoNextActivity(String ticketId) {
//        Intent i = new Intent(QR_CodeReaderActivity.this,PayPalPaymentActivity.class);
//        i.putExtra("ticketInfo",ticketId);
//        startActivity(i);
//    }
}
