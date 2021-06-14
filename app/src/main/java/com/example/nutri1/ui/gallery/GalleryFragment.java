package com.example.nutri1.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

public class GalleryFragment extends Fragment {

    ImageView mfruitimage;
    android.widget.Button mselectbutton,mpredictbutton;
    TextView mfruittext,mft2;
    Bitmap img;

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

                     if(outputFeature0.getFloatArray()[0]>5.0){
                         mfruittext.setText("Apple");

                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Good for Heart\n\t\t\t" +
                                 "b) Lowers Risk of Diabetes\n\t\t\t" +
                                 "c) Promote Good Gut Bacteria\n\n\t\t"+
                                 "Nutrients:\n\t\t\t" +
                                 "• Calories: 52.\n\t\t\t" +
                                 "• Water: 86%\n\t\t\t" +
                                 "• Protein: 0.3 grams.\n\t\t\t" +
                                 "• Carbs: 13.8 grams.\n\t\t\t" +
                                 "• Sugar: 10.4 grams.\n\t\t\t" +
                                 "• Fiber: 2.4 grams.\n\t\t\t" +
                                 "• Fat: 0.2 grams");
                     }
                     else if(outputFeature0.getFloatArray()[7]>5.0){
                         mfruittext.setText("rotten mango");
                         mft2.setText("\t\tFresh:\t\t\tNo\n\n\t\t" +
                                 "Benefits of Mangoes:\n\t\t\t" +
                                 "a) Good for Weight control\n\t\t\t" +
                                 "b) Improves digestion\n\t\t\t" +
                                 "c) Improved immunity\n\n\t\t"+
                                 "Recommendation:\n\t\t\t" +
                                 "• Better not to eat" );
                     }
                     else{
                         mfruittext.setText("none");
                     }


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