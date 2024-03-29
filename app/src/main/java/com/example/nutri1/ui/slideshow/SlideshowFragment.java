package com.example.nutri1.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutri1.R;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private SlideshowViewModel slideshowViewModel;
    Button b,a,o,t,m,c;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

    b= root.findViewById(R.id.banana);
        o= root.findViewById(R.id.orange);
        c= root.findViewById(R.id.cantaloupe);
        a= root.findViewById(R.id.apple);
        t= root.findViewById(R.id.tomato);
        m= root.findViewById(R.id.mango);

    b.setOnClickListener(this);
    o.setOnClickListener(this);
    c.setOnClickListener(this);
    t.setOnClickListener(this);
    a.setOnClickListener(this);
    m.setOnClickListener(this);
    return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banana: gotourl("https://en.wikipedia.org/wiki/Banana");
                break;
            case R.id.orange: gotourl("https://en.wikipedia.org/wiki/Orange_(fruit)");
                break;
            case R.id.cantaloupe: gotourl("https://en.wikipedia.org/wiki/Cantaloupe");
                break;
            case R.id.apple: gotourl("https://en.wikipedia.org/wiki/Apple");
                break;
            case R.id.tomato: gotourl("https://en.wikipedia.org/wiki/Tomato");
                break;
            case R.id.mango: gotourl("https://en.wikipedia.org/wiki/Mango");
                break;
        }

    }
    private void gotourl(String s){
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}