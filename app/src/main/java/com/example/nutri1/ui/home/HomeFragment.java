package com.example.nutri1.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutri1.R;

public class HomeFragment extends Fragment {
    android.widget.Button mcalculatebmi;
    TextView mcurrentheight;
    TextView mcurrentage,mcurrentweight;
    ImageView mincrementage,mincrementweight,mdecrementweight,mdecrementage;
    SeekBar mseekbarforheight;
    RelativeLayout mmale,mfemale;
    int intweight=65,intage=22,currentprogress;
    String mintprogress="165";
    String typeofuser="0";
    String weight2="65",age2="22";

    private HomeViewModel homeViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { }
        });


    mcalculatebmi=root.findViewById(R.id.calculatebmi);

    mcurrentage=root.findViewById(R.id.currentage);
    mcurrentheight=root.findViewById(R.id.currentheight);
    mcurrentweight=root.findViewById(R.id.currentweight);
    mincrementage=root.findViewById(R.id.incrementage);
    mdecrementage=root.findViewById(R.id.decrementage);
    mincrementweight=root.findViewById(R.id.incrementweight);
    mdecrementweight=root.findViewById(R.id.decrementweight);
    mseekbarforheight=root.findViewById(R.id.seekbarforheight);
    mmale=root.findViewById(R.id.male);
    mfemale=root.findViewById(R.id.female);

    mmale.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mmale.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.malefemale));
            mfemale.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.malefemalenotfocus));
            typeofuser="Male";



        }
    });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfemale.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.malefemale));
                mmale.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.malefemalenotfocus));
                typeofuser="Female";

            }
        });

        mseekbarforheight.setMax(200);
        mseekbarforheight.setMin(80);
        mseekbarforheight.setProgress(165);

        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentprogress=progress;
                mintprogress=String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mincrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage+1;
                age2=String.valueOf(intage);
                mcurrentage.setText(age2);

            }
        });

        mdecrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage-1;
                age2=String.valueOf(intage);
                mcurrentage.setText(age2);

            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

            }
        });

        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

            }
        });


    mcalculatebmi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (typeofuser.equals("0")){
                Toast.makeText(getActivity().getApplicationContext(), "Select Your Gender",Toast.LENGTH_SHORT).show();
            }
            else if(mintprogress.equals("0")){
                Toast.makeText(getActivity().getApplicationContext(), "Select Your height",Toast.LENGTH_SHORT).show();

            }
            else if(intage==0||intage<0){
                Toast.makeText(getActivity().getApplicationContext(), "age is incorrect",Toast.LENGTH_SHORT).show();

            }
            else if(intweight==0||intweight<0){
                Toast.makeText(getActivity().getApplicationContext(), "Weight is incorrect",Toast.LENGTH_SHORT).show();

            }
            else{



            Intent intent=new Intent(getActivity(), bmiactivity1.class);
            intent.putExtra("gender",typeofuser);
            intent.putExtra("height",mintprogress);
            intent.putExtra("weight",weight2);
            intent.putExtra("age",age2);


                    startActivity(intent);}

        }
    });

        return root;
    }
}