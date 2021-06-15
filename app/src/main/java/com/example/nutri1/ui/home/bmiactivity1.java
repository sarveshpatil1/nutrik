package com.example.nutri1.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
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
Integer ibmi;
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
        mbmicategory.setMovementMethod(new ScrollingMovementMethod());
        mgender=findViewById(R.id.genderdisplay);
        mbackground=findViewById(R.id.contentlayout);
        mimageview=findViewById(R.id.imageview);


        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");
        intheight=Float.parseFloat(height);
        intweight=Float.parseFloat(weight);
        intheight=intheight/100;
        intbmi=intweight/(intheight*intheight);
        ibmi=Math.round(intbmi);

        mbmi=Integer.toString(ibmi);

        if(intbmi<18){
            mbmicategory.setText("You are Underweight\n\nFruit Recommendation: Avacardos and Mangoes\n\t" +
                    "• These help to maintain good body weight");
            mbackground.setBackgroundColor(Color.LTGRAY);
            mimageview.setImageResource(R.drawable.notok);
        }
        else if(intbmi>18 && intbmi<25){
            mbmicategory.setText("Your BMI is perfect\n\nEat healthy fruits like\n\t" +
                    "• bananas for gut health");
            mbackground.setBackgroundResource(R.drawable.grad_button2);
            mimageview.setImageResource(R.drawable.ok);
        }
        else if(intbmi>25 && intbmi<30){
            mbmicategory.setText("You are overweight\n\nFruit Recommendation: Apples and kiwis\n\t" +
                    "• These fruits are great for weight loss");
            mbackground.setBackgroundResource(R.drawable.grad_o);
            mimageview.setImageResource(R.drawable.notok);
        }
        else if(intbmi>30 && intbmi<41){
            mbmicategory.setText("You are obese\n\nFruit Recommendation: Berries,Grapefruit and Apples\n\t" +
                    "• Also consult a Dietitian for proper guidance");
            mbackground.setBackgroundResource(R.drawable.grad_a);
            mimageview.setImageResource(R.drawable.notok);
        }
        else if(intbmi>41 && intbmi<60){
            mbmicategory.setText("Extreme obese consult a doctor soon");
            mbackground.setBackgroundResource(R.drawable.grad_t);
            mimageview.setImageResource(R.drawable.notok);
        }
        else {
            mbmicategory.setText("Please input your Correct Weight and Height");
            mbackground.setBackgroundResource(R.drawable.grad_t);
            mimageview.setImageResource(R.drawable.notok);
        }
        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);



        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bmiactivity1.this, HomeFragment.class) ;
                finish();
            }
        });
    }
}