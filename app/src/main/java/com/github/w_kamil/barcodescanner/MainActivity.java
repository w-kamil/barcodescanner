package com.github.w_kamil.barcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String scanStringContent;
    private String imagePath;
    private String scanFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            scanStringContent = savedInstanceState.getString("scanStringContent");
            imagePath = savedInstanceState.getString("imagePath");
        }
        TextView textView = (TextView) findViewById(R.id.scan_content);
        textView.setText(scanStringContent);

        Button scamButton = (Button) findViewById(R.id.scan_button);
        scamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Anulowano", Toast.LENGTH_LONG).show();
            } else {
                scanFormat = result.getFormatName();
                scanStringContent = result.getContents();
                imagePath = result.getBarcodeImagePath();

                Toast.makeText(this, "Wynik: " + scanStringContent, Toast.LENGTH_LONG).show();

                TextView scanFormatTextView = (TextView) findViewById(R.id.scan_format);
                scanFormatTextView.setText(scanFormat);

                TextView textView = (TextView) findViewById(R.id.scan_content);
                textView.setText(scanStringContent);

                ImageView imageView = (ImageView) findViewById(R.id.image_view);

                File file = new File(imagePath);
                if (file.exists()) {
                    Bitmap bImage = BitmapFactory.decodeFile(imagePath);
                    imageView.setImageBitmap(bImage);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("scanStringContent", scanStringContent);
        outState.putString("imagePath", imagePath);

    }
}


