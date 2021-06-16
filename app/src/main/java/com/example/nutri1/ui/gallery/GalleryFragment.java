package com.example.nutri1.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.text.Spanned;
import com.example.nutri1.R;
import com.example.nutri1.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

public class GalleryFragment extends Fragment {

    ImageView mfruitimage;
    android.widget.Button mselectbutton,mpredictbutton;
    TextView mfruittext,mft2;
    Bitmap img;
    DecimalFormat df=new DecimalFormat("#.###");

    private GalleryViewModel galleryViewModel;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
       // final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { }
        });

    mfruitimage=(ImageView) root.findViewById(R.id.fruitimage);
    mfruittext=(TextView) root.findViewById(R.id.fruittext);
    mselectbutton=root.findViewById(R.id.selectbutton);
    mpredictbutton=root.findViewById(R.id.predictbutton);
    mft2=(TextView) root.findViewById(R.id.fruittext2);
    mft2.setMovementMethod(new ScrollingMovementMethod());


        mselectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent,100);
            }
        });
        mpredictbutton.setOnClickListener(new View.OnClickListener() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onClick(View v) {

                img=Bitmap.createScaledBitmap(img,224,224,true);

                 try {
                     Model model = Model.newInstance(getActivity().getApplicationContext());

                     // Creates inputs for reference.
                     TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
                     TensorImage tensorImage=new TensorImage(DataType.UINT8);
                     tensorImage.load(img);
                     ByteBuffer byteBuffer=tensorImage.getBuffer();


                     inputFeature0.loadBuffer(byteBuffer);

                     // Runs model inference and gets result.
                     Model.Outputs outputs = model.process(inputFeature0);
                     TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


                     // Releases model resources if no longer used.
                     model.close();
                     mfruittext.setText("Test mode");
                    mft2.setText(df.format(outputFeature0.getFloatArray()[0]/255.0)+"   - apple\n"+
                            df.format(outputFeature0.getFloatArray()[1]/255.0)+"   - Rotten apple\n"+
                            df.format(outputFeature0.getFloatArray()[2]/255.0)+"  - Banana\n"+
                            df.format(outputFeature0.getFloatArray()[3]/255.0)+"  - Rotten Banana\n"+
                            df.format(outputFeature0.getFloatArray()[4]/255.0)+"  - Orange\n"+
                            df.format(outputFeature0.getFloatArray()[5]/255.0)+"  - Rotten Orange\n"+
                            df.format(outputFeature0.getFloatArray()[6]/255.0)+"  - Tomato\n"+
                            df.format(outputFeature0.getFloatArray()[7]/255.0)+"  - Not ripen tomato\n"+
                            df.format(outputFeature0.getFloatArray()[8]/255.0)+"  - strawberry\n");
                   /*  if(outputFeature0.getFloatArray()[1]>50.0){
                         mfruittext.setText("Apple");
                         mft2.setText(R.string.s_apple);
                     }

                     else if(outputFeature0.getFloatArray()[1]>50.0){
                         mfruittext.setText("rotten Apple");
                         mft2.setText(R.string.r_apple);
                     }
                     else if(outputFeature0.getFloatArray()[2]>50.0){
                         mfruittext.setText("Orange");
                         mft2.setText(R.string.s_orange);
                     }
                     else if(outputFeature0.getFloatArray()[3]>50.0){
                         mfruittext.setText("rotten Orange");
                         mft2.setText(R.string.r_orange);
                     }


                     else if(outputFeature0.getFloatArray()[4]>50.0){
                         mfruittext.setText("Banana");
                         mft2.setText(R.string.s_banana);
                     }
                     else if(outputFeature0.getFloatArray()[5]>50.0){
                         mfruittext.setText("rotten Banana");
                         mft2.setText(R.string.r_banana);
                     }
                     else if(outputFeature0.getFloatArray()[6]>50.0){
                         mfruittext.setText("Tomato");
                         mft2.setText(R.string.s_tomato);
                     }
                     else if(outputFeature0.getFloatArray()[7]>50.0){
                         mfruittext.setText("not ripned tomoato");
                         mft2.setText(R.string.n_tomato);
                     }

                     else if(outputFeature0.getFloatArray()[8]>50.0){
                         mfruittext.setText("Strawberries");
                         mft2.setText(R.string.s_straw);
                     }


                     else{
                         mfruittext.setText("none");
                     }*/


                 } catch (IOException e) {
                     // TODO Handle the exception
                 }


             }
                });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            mfruitimage.setImageURI(data.getData());
            Uri uri=data.getData();
            try {
                img= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}