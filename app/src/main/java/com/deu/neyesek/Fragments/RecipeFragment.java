package com.deu.neyesek.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deu.neyesek.Adapters.RecipeAdapter;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private HomeFragment.OnFragmentInteractionListener mListener;
    String aan = "";
    RecyclerView recipeRecyclerView ;
    RecipeAdapter recipeAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    DatabaseReference databaseReference2 ;

    List recipeList;
    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Handle the back pressed
                View fragmentView2 = inflater.inflate(R.layout.fragment_home, container, false);
                getParentFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment() ).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeRecyclerView  = fragmentView.findViewById(R.id.recipeRV);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Recipe");


        return fragmentView;
    }

    public void onStart() {
        super.onStart();

        // Get List Posts from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                recipeList = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());
                    Recipe recipe = new Recipe();
                    Map<String, String> map = (Map) postsnap.getValue();
                    recipe.setRecipeKey(postsnap.getKey());

                    recipe.setName(map.get("Name"));
                    recipe.setShortDescription(map.get("ShortDescription"));
                    recipe.setImage(map.get("Image"));
                    recipe.setPrepDetails(map.get("PrepDetails"));
                    recipe.setIngridients(map.get("Ingridients"));

                    recipe.setCategoryBread(map.get("CategoryBread"));
                    recipe.setCuisine(map.get("Cuisine"));
                    recipe.setMainCategory(map.get("Category"));

                    recipe.setIngridientNames(map.get("IngridientNames"));
                    recipe.setKeywords(map.get("Keywords"));
                    recipe.setRecipeDetails(map.get("RecipeDetails"));
                    //System.out.println(map.get("RecipeDetails"));


                    recipe.setRecipeDetails(aan);
                   //String prep = map.get("RecipeDetails");

                    recipeList.add(recipe);


                }

                recipeAdapter = new RecipeAdapter(getActivity(), recipeList);
                recipeRecyclerView.setAdapter(recipeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}