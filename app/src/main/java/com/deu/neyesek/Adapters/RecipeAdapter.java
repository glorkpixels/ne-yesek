package com.deu.neyesek.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.neyesek.Fragments.RecipeFragment;

import com.bumptech.glide.Glide;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    Context mContext;
    List<Recipe>mData ;
    public String recipeID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference referenceStudent, referenceTeacher;
    FirebaseUser currentUser;
    String name;
    String te;
    private static final String TAG = "MainActivity";


    public RecipeAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_recipe_item,parent,false);
        return new MyViewHolder(row);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getName());
        holder.tvDesc.setText(mData.get(position).getShortDescription());
        Glide.with(mContext).load(mData.get(position).getImage()).into(holder.imgPost);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDesc;
        ImageView imgPost;
        Button deletecourse;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_recipe_title);
            tvDesc= itemView.findViewById(R.id.row_recipe_description);
            imgPost = itemView.findViewById(R.id.row_recipe_img);
            deletecourse = itemView.findViewById(R.id.delete_recipe_button);
/*
// delete button is bugged so its commented for now
         //   deletecourse.setVisibility(View.VISIBLE);
            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Teacher/");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot2) {
                    for (DataSnapshot postsnap: dataSnapshot2.getChildren()) {
                        Chat chat = postsnap.getValue(Chat.class);


                        if (chat.getUserType().equals("teacher")){
                            te = chat.getUserType();
                        }
                        else{
                            te = "student";
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }});


            deletecourse.setVisibility(View.VISIBLE);
                System.out.println(te + " OFFFFFFFFFFFFFFFFFFFFFF");
            if ("teacher".equals(te))
              {
                  deletecourse.setOnClickListener(new View.OnClickListener() {
                      public void onClick(View v) {

                          final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Course").child(courseID);

                          ref.addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                      ref.getRef().removeValue();
                                  }
                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError) {
                                  Log.e(TAG, "onCancelled", databaseError.toException());
                              }
                          });








                      }
                  });
              }
            else {

                deletecourse.setVisibility(View.INVISIBLE);
            }

*/





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    recipeID = mData.get(position).getRecipeKey();
                    // will fix this later i forgot to add user name to post object
                    //postDetailActivity.putExtra("userName",mData.get(position).getUsername);
                    // long timestamp  = (long) mData.get(position).getTimeStamp();
                    //     postDetailActivity.putExtra("postDate",timestamp) ;
                    Bundle bundle = new Bundle();
                    bundle.putString("recipeID",recipeID); // Put anything what you want

                    RecipeFragment fragment2 = new RecipeFragment();
                    fragment2.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportActionBar().setTitle("Recipes");

                   activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment2)
                            .commit();




                }
            });

        }


    }
}
