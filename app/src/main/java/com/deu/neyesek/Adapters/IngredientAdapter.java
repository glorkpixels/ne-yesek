package com.deu.neyesek.Adapters;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.neyesek.Activity.IngredientDetailActivity;
import com.deu.neyesek.Activity.RecipeDetailActivity;
import com.deu.neyesek.Fragments.RecipeFragment;

import com.bumptech.glide.Glide;
import com.deu.neyesek.Models.Ingredient;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.Models.mList;
import com.deu.neyesek.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder>{


    Context mContext;
    List<Ingredient> mData;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String ingKey;
    // this post adapter gets posts when called and projects it to fragment post fragment
    public IngredientAdapter(Context mContext, List<Ingredient> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_ingredient_item, parent, false);
        return new MyViewHolder(row);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getTurkishName());
        holder.tvDesc.setText(mData.get(position).getServingSize());
        holder.tvCal.setText(mData.get(position).getCalorie());
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        holder.buttonFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            //animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            compoundButton.startAnimation(scaleAnimation);



        });

        holder.buttonShopList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            //animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            compoundButton.startAnimation(scaleAnimation);



        });

        holder.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = firebaseDatabase.getReference("UserFavorites").child(firebaseUser.getUid()).child("Ingredient").push();
                mList ingre = new mList();
                ingre.setcKey(mData.get(position).getTurkishName());
                databaseReference.setValue(ingre).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        holder.buttonShopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference2 = firebaseDatabase.getReference("UserShoppingList").child(firebaseUser.getUid()).push();
                mList ingre = new mList();
                ingre.setcKey(mData.get(position).getTurkishName());
                databaseReference2.setValue(ingre).addOnSuccessListener(new OnSuccessListener<Void>() {
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



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvCal;
        TextView tvDesc;
        CompoundButton buttonFavorite;
        CompoundButton buttonShopList;
        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_ingredient_title);
            tvDesc = itemView.findViewById(R.id.row_ingredient_serving_size);
            tvCal = itemView.findViewById(R.id.row_ingredient_calorie);
            buttonFavorite = itemView.findViewById(R.id.button_favorite_ing);
            buttonShopList = itemView.findViewById(R.id.button_shop_ing);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent IngredientDetailActivity = new Intent(mContext, IngredientDetailActivity.class);
                    int position = getAdapterPosition();
                    System.out.println( mData.get(position).getIngredientKey() + " lol");
                    IngredientDetailActivity.putExtra("Key", mData.get(position).getIngredientKey());
                    IngredientDetailActivity.putExtra("Name", mData.get(position).getTurkishName());

                    IngredientDetailActivity.putExtra("descshort", mData.get(position).getShortDesc());

                    IngredientDetailActivity.putExtra("desc", mData.get(position).getStringOfValues());
                    mContext.startActivity(IngredientDetailActivity);

                }
            });

        }


    }
}
