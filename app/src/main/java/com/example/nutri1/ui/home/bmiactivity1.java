package com.example.nutri1.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nutri1.R;

public class bmiactivity1 extends AppCompatActivity {
android.widget.Button mrecalculatebmi;
TextView mbmidisplay,mbmicategory,mgender;
Intent intent;
ImageView mimageview;
String mbmi;
Float intbmi;
String height;
String weight;
Float intheight,intweight;
RelativeLayout mbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity1);
        mrecalculatebmi=findViewById(R.id.recalculatebmi);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\"></font>"));
        getSupportActionBar().setTitle("B.M.I");
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#1E1D1D"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        intent=getIntent();
        mbmidisplay=findViewById(R.id.bmidisplay);
        mbmicategory=findViewById(R.id.bmicategory);
        mgender=findViewById(R.id.genderdisplay);
        mbackground=findViewById(R.id.contentlayout);
        mimageview=findViewById(R.id.imageview);


        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");
        intheight=Float.parseFloat(height);
        intweight=Float.parseFloat(weight);
        intheight=intheight/100;
        intbmi=intweight/(intheight*intheight);


        mbmi=Float.toString(intbmi);
        if(intbmi<18){
            mbmicategory.setText("You are Underweight\nFruit Recommendation:Avacardos and Mangoes");
            mbackground.setBackgroundColor(Color.LTGRAY);
            mimageview.setImageResource(R.drawable.notok);
        }
        else if(intbmi>18 && intbmi<25){
            mbmicategory.setText("Your BMI is perfect\n Eat healthy fruits like bananas for gut health");
            mbackground.setBackgroundColor(Color.GREEN);
            mimageview.setImageResource(R.drawable.ok);
        }
        else if(intbmi>25 && intbmi<30){
            mbmicategory.setText("You are overweight\nFruit Recommendation:Apples and kiwis\nThese fruits are great for weight loss");
            mbackground.setBackgroundColor(Color.YELLOW);
            mimageview.setImageResource(R.drawable.notok);
        }
        else if(intbmi>30){
            mbmicategory.setText("You are obese\nFruit Recommendation:Berries,Grapefruit and Apples\nAlso consult a Dietitian for proper guidance");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.notok);
        }
        else {
            mbmicategory.setText("Extreme obese consult a doctor soon");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.notok);
        }

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(Integer.parseInt(mbmi));



        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bmiactivity1.this, HomeFragment.class) ;
                finish();
            }
        });
    }
}