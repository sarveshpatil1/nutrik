package com.example.nutri1.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nutri1.R;

public class bmiactivity1 extends AppCompatActivity {
android.widget.Button mrecalculatebmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity1);
        mrecalculatebmi=findViewById(R.id.recalculatebmi);
        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bmiactivity1.this, HomeFragment.class) ;
                finish();
            }
        });
    }
}