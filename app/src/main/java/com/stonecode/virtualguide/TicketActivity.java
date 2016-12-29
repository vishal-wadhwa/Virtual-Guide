package com.stonecode.virtualguide;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketActivity extends AppCompatActivity {

    private ImageView paytm;
    private ImageView addVis;
    private TextView numVis;
    private TextView toPay;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        paytm = (ImageView) findViewById(R.id.imageView2);
        toPay = (TextView) findViewById(R.id.textView3);
        numVis = (TextView) findViewById(R.id.txt_num_vis);
        addVis = (ImageView) findViewById(R.id.imageView);
        addVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                numVis.setText(String.valueOf(count));
                int x = count * 90;
                toPay.setText(String.valueOf(x));
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TicketActivity.this, VerifyBarCode.class);
                startActivity(i);
            }
        });
    }
}
