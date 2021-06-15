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
                    /* if(outputFeature0.getFloatArray()[0]>5.0){
                         mfruittext.setText("Apple");
                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Good for Heart\n\t\t\t" +
                                 "b) Lowers Risk of Diabetes\n\t\t\t" +
                                 "c) Promote Good Gut Bacteria\n\n\t\t"+
                                 "Nutrients:\n\t\t\t" +
                                 "• Calories: 130.\n\t\t\t" +
                                 "• Water: 86%\n\t\t\t" +
                                 "• Protein: 1 grams.\n\t\t\t" +
                                 "• Carbs: 34 grams.\n\t\t\t" +
                                 "• Sugar: 25 grams.\n\t\t\t" +
                                 "• Fiber: 5 grams.\n\t\t\t" +
                                 "• Fat: 0.2 grams");
                     }
                     else if(outputFeature0.getFloatArray()[1]>5.0){
                         mfruittext.setText("rotten Apple");
                         mft2.setText("\tFresh:\t\tNo\n\n\t" +
                                         "Recommendation:\n\t\t" +
                                         "• Better not to eat\n\n\t"+
                                 "Benefits of Apples:\n\t\t" +
                                 "a) Lowers Risk of Diabetes\n\t\t" +
                                 "b) Promote Good Gut Bacteria");
                     }
                     else if(outputFeature0.getFloatArray()[2]>5.0){
                         mfruittext.setText("Orange");
                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Boosts your immune system\n\t\t\t" +
                                 "b) Good for Weight control\n\t\t\t" +
                                 "c) Helps to maintain skin health\n\n\t\t"+
                                 "Nutrients:\n\t\t\t" +
                                 "• Calories: 80.\n\t\t\t" +
                                 "• Water: 87%\n\t\t\t" +
                                 "• Vit A: 14 milligrams.\n\t\t\t" +
                                 "• Vit B: 11.8 grams.\n\t\t\t" +
                                 "• Sugar: 14 grams.\n\t\t\t" +
                                 "• Fiber: 3 grams.\n\t\t\t" +
                                 "• Fat: 0.1");
                     }
                     else if(outputFeature0.getFloatArray()[3]>5.0){
                         mfruittext.setText("rotten Orange");
                         mft2.setText("\tFresh:\t\tNo\n\n\t" +
                                 "Recommendation:\n\t\t" +
                                 "• Better not to eat\n\n\t"+
                                 "• Can be used for composting\n\n\t"+
                                 "Benefits of Orange:\n\t\t" +
                                 "a) Boosts your immune system\n\t\t" +
                                 "b) Good for Weight control\n\t\t" +
                                 "c) Helps to maintain skin health");
                     }


                     else if(outputFeature0.getFloatArray()[4]>5.0){
                         mfruittext.setText("Banana");
                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Good for Digestive health\n\t\t\t" +
                                 "b) Lowers Risk of Diabetes\n\t\t\t" +
                                 "c) Promote Good Gut Bacteria\n\t\t\t"+
                                 "c) Helps manage Blood pressure\n\n\t\t\t"+
                                 "Nutrients:\n\t\t\t" +
                                 "• Calories: 110.\n\t\t\t" +
                                 "• Water: 86%\n\t\t\t" +
                                 "• Carbs: 30 grams.\n\t\t\t" +
                                 "• Sugar: 19 grams.\n\t\t\t" +
                                 "• Fiber: 3 grams.\n\t\t\t" +
                                 "• Fat: 0.1 grams");
                     }
                     else if(outputFeature0.getFloatArray()[5]>5.0){
                         mfruittext.setText("rotten Banana");
                         mft2.setText("\tFresh:\t\tNo\n\n\t" +
                                 "Recommendation:\n\t\t" +
                                 "• Better not to eat\n\n\t"+
                                "a) Good for Digestive health\n\t\t" +
                                 "b) Lowers Risk of Diabetes\n\t\t" +
                                 "c) Promote Good Gut Bacteria\n\t\t"+
                                 "c) Helps manage Blood pressure\n\t\t"+);
                     }
                     else if(outputFeature0.getFloatArray()[6]>5.0){
                         mfruittext.setText("Tomato");
                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Good for Eyes and skin\n\t\t\t" +
                                 "b) Controls blood sugar level\n\t\t\t" +
                                 "c) Helps in body hydration\n\n\t\t"+
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
                         mfruittext.setText("not ripned tomoato");
                         mft2.setText("\tFresh:\t\tNo\n\n\t" +
                                 "Recommendation:\n\t\t" +
                                 "• Better not to eat\n\n\t"+
                                 "Benefits of Tomato:\n\t\t" +
                                 "a) Good for Eyes and skin\n\t\t" +
                                 "b) Controls blood sugar level\n\t\t" +
                                 "c) Helps in body hydration");
                     }

                     else if(outputFeature0.getFloatArray()[8]>5.0){
                         mfruittext.setText("Strawberries");
                         mft2.setText("\t\tFresh:\t\t\tYES\n\n\t\t" +
                                 "Benefits:\n\t\t\t" +
                                 "a) Good for  Blood pressure\n\t\t\t" +
                                 "b) Lowers Risk of Diabetes\n\t\t\t" +
                                 "c) Promote Good Gut Bacteria\n\t\t\t"+
                                 "d) Helps to Prevent strokes\n\n\t\t"+
                                 "Nutrients:\n\t\t\t" +
                                 "• Calories: 100.\n\t\t\t" +
                                 "• Water: 91%\n\t\t\t" +
                                 "• Protein: 0.7 grams.\n\t\t\t" +
                                 "• Carbs: 7.7 grams.\n\t\t\t" +
                                 "• Sugar: 8 grams.\n\t\t\t" +
                                 "• Fiber: 2 grams.\n\t\t\t" +
                                 "• Fat: 0.3 grams");
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