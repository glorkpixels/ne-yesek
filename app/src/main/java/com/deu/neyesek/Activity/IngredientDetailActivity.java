package com.deu.neyesek.Activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deu.neyesek.Adapters.CommentAdapter;
import com.deu.neyesek.Models.Comment;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.Models.mList;
import com.deu.neyesek.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IngredientDetailActivity extends AppCompatActivity {

    TextView txtPostDesc,txtPostDateName,txtPostTitle,txtPostPrep ,txtPostShorts,txtPostDateName2;

    String PostKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CompoundButton buttonFavorite;


    CompoundButton buttonShop;
    FirebaseDatabase firebaseDatabase;

    String postsh ="";
    String postxd ="";

    FirebaseUser currentUser;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        // let's set the statue bar to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        // ini Views
        txtPostTitle = findViewById(R.id.ingredient_detail_title);
        txtPostDateName = findViewById(R.id.ingredient_detail_date_name);
        txtPostDateName2 = findViewById(R.id.ingredient_detail_date_name2);
        txtPostDesc = findViewById(R.id.ingredient_detail_desc);
        txtPostShorts = findViewById(R.id.ingredient_steps);

        buttonFavorite = findViewById(R.id.button_favorite);
        buttonShop = findViewById(R.id.button_shop);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // add Comment button click listner
        String postTitle = getIntent().getExtras().getString("Name");
        txtPostTitle.setText(postTitle);
        String postsc = (String) getIntent().getExtras().getString("desc");
        txtPostShorts.setText(postsc);

        String postDescription = (String) getIntent().getExtras().getString("descshort");
        txtPostDesc.setText(postDescription);
        PostKey = "";
        PostKey = getIntent().getExtras().getString("Key");
        //System.out.println(PostKey + "keybumu");
        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Recipe");
        postxd = "";
        postsh = "";

        txtPostDateName.setText("Add to favorites ");

        txtPostDateName2.setText("Add to cellar ");



        buttonFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            //animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            compoundButton.startAnimation(scaleAnimation);

        });

        buttonShop.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            //animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            compoundButton.startAnimation(scaleAnimation);
        });
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = firebaseDatabase.getReference("UserFavorites").child(firebaseUser.getUid()).child("Ingredient").push();

                mList meal = new mList();
                meal.setcKey(PostKey);
                myRef.setValue(meal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = firebaseDatabase.getReference("UserCellarList").child(firebaseUser.getUid()).push();

                mList meal = new mList();
                meal.setcKey(PostKey);

                myRef.setValue(meal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


            }
        });

    }
}