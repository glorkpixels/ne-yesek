package com.deu.neyesek.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deu.neyesek.Activity.RecipeDetailActivity;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {


    Context mContext;
    List<Recipe> mData;

    // this post adapter gets posts when called and projects it to fragment post fragment
    public MenuAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_menu_item, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getName());
        holder.tvDesc.setText(mData.get(position).getShortDescription());
        holder.tving.setText("Day"+ mData.get(position).getDayc() + "\n" + mData.get(position).getTimeofDay());
        Glide.with(mContext).load(mData.get(position).getImage()).into(holder.imgPost);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tving;
        ImageView imgPost;
        TextView tvDesc;
        ImageView imgPostProfile;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_recipe_title2);
            tving = itemView.findViewById(R.id.row_menu_details);
            tvDesc = itemView.findViewById(R.id.row_recipe_description2);
            imgPost = itemView.findViewById(R.id.row_recipe_img2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(mContext, RecipeDetailActivity.class);
                    int position = getAdapterPosition();
                    System.out.println(mData.get(position).getName() + mData.get(position).getRecipeKey() + " lol");
                    postDetailActivity.putExtra("Name", mData.get(position).getName());
                    postDetailActivity.putExtra("Image", mData.get(position).getImage());
                    postDetailActivity.putExtra("xd", mData.get(position).getIngridients());
                    postDetailActivity.putExtra("sdd", mData.get(position).getRecipeDetails());
                    //System.out.println(mData.get(position).getRecipeDetails() + " lol");
                    //postDetailActivity.putExtra("prepdet", mData.get(position).getPrepDetails());
                    postDetailActivity.putExtra("postKey", mData.get(position).getRecipeKey());
                    // will fix this later i forgot to add user name to post object
                   // postDetailActivity.putExtra("userId", mData.get(position).getIngridients());

                    mContext.startActivity(postDetailActivity);
                    // if any post clicked we call post detail activity to show of post details and comments of it

                }
            });

        }


    }
}
