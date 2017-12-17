package com.example.bunnyqueen.onlinebustickets;

import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR_CodeGeneratorActivity extends AppCompatActivity {

    EditText text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code_generator);

        String busInfo = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            busInfo = extras.getString("busInfo");
        }
        TextView busInfoDisplay = (TextView) findViewById(R.id.busInfo);
        busInfoDisplay.setText(busInfo);

        text = (EditText) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode("text2QR", BarcodeFormat.QR_CODE, 480, 480);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        } catch (WriterException we) {
            we.printStackTrace();
        }

    }
}
