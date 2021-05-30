package com.deu.neyesek.Activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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
import com.deu.neyesek.Adapters.RecipeAdapter;
import com.deu.neyesek.Models.Comment;
import com.deu.neyesek.Models.Recipe;
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
import java.util.Map;

public class RecipeDetailActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    ImageView imgPost,imgCurrentUser;
    TextView txtPostDesc,txtPostDateName,txtPostTitle,txtPostPrep ,txtPostShorts,txtPostDateName2;
    EditText editTextComment;
    Button btnAddComment;
    String PostKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CompoundButton buttonFavorite;

    CompoundButton buttonShop;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;


    String ingredientNames;
    String postsh ="";
    String postxd ="";
    List<Comment> listComment;


    static String COMMENT_KEY = "Comment" ;
    String NAME ="";

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

// let's set the statue bar to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

// ini Views
        RvComment = findViewById(R.id.rv_comment);
        imgPost =findViewById(R.id.recipe_detail_img);
        imgCurrentUser = findViewById(R.id.recipe_detail_currentuser_img);

        txtPostTitle = findViewById(R.id.recipe_detail_title);

        txtPostDesc = findViewById(R.id.recipe_detail_desc);
        txtPostDateName = findViewById(R.id.recipe_detail_date_name);
        txtPostDateName2 = findViewById(R.id.recipe_detail_date_name2);
        txtPostPrep = findViewById(R.id.recipe_prep_steps);
        txtPostShorts = findViewById(R.id.recipe_prep_detail);

        editTextComment = findViewById(R.id.recipe_detail_comment);
        btnAddComment = findViewById(R.id.recipe_detail_add_comment_btn);
        buttonFavorite = findViewById(R.id.button_favorite);
        buttonShop = findViewById(R.id.button_shop);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // add Comment button click listner
        String postImage = getIntent().getExtras().getString("Image") ;
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("Name");
        txtPostTitle.setText(postTitle);
        txtPostDateName.setText("Add to favorites ");

        txtPostDateName2.setText("Add to cart ");
        String postDescription = getIntent().getExtras().getString("xd");
        txtPostDesc.setText(postDescription);
        PostKey = "";
        PostKey = getIntent().getExtras().getString("postKey");
        //System.out.println(PostKey + "keybumu");
        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Recipe");
        postxd = "";
        postsh = "";
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                    if(postsnap.getKey().equals(PostKey)){
                        //System.out.println(postsnap.getKey() + "evet bu");
                        Recipe recipe = postsnap.getValue(Recipe.class);
                        //String prep = map.get("RecipeDetails");
                        postxd = recipe.getRecipeDetails();
                        postsh += " MIKTAR       HAZIRLIK       PİŞİRME \n |   ";
                        postsh += recipe.getPrepDetails();

                        postsh = postsh.replace(";", "   |    ");
                        txtPostShorts.setText(postsh);
                        txtPostPrep.setText(postxd);

                        ingredientNames = recipe.getIngridientNames();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        buttonFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
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

                DatabaseReference myRef = firebaseDatabase.getReference("UserFavorites").child(firebaseUser.getUid()).child("Meals").child(PostKey).push();

                myRef.setValue(PostKey).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


                DatabaseReference databaseReference4 = firebaseDatabase.getReference("UserFavorites").child("Meals").child(firebaseUser.getUid());

                /*
                databaseReference4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(PostKey).exists()) {
                            databaseReference4.child(PostKey).getRef().removeValue();
                        }

                        else{
                            databaseReference4.child(PostKey);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                */


            }
        });

        buttonShop.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            //animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            compoundButton.startAnimation(scaleAnimation);
        });

        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = firebaseDatabase.getReference("UserShoppingList").child(firebaseUser.getUid()).push();

                ingredientNames.replace(";"," \n");

                    myRef.setValue(ingredientNames).addOnSuccessListener(new OnSuccessListener<Void>() {
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



        btnAddComment.setOnClickListener(view -> {

            btnAddComment.setVisibility(View.INVISIBLE);
            DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
            // database reference of postkey under comment branch
            String comment_content = editTextComment.getText().toString();
            String uid = firebaseUser.getUid();
            String uname = firebaseUser.getEmail();
            String uimg = "";
            Comment comment = new Comment(comment_content,uid,uimg,uname);

            // comment adding listener from sent button
            commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showMessage("comment added");
                    editTextComment.setText("");
                    btnAddComment.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("failed to add comment : "+e.getMessage());
                }
            });
        });



// loading post infos user names
        // ini Recyclerview Comment
        iniRvComment();


    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));
// if there is new comments or comments that are not loaded this works and calls comment adapter comment adapter will take new comments andd add them
        // to recycler view
        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;


    }

}