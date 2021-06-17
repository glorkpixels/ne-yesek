package com.deu.neyesek.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.neyesek.Adapters.IngredientAdapter;
import com.deu.neyesek.Adapters.RecipeAdapter;
import com.deu.neyesek.Models.Ingredient;
import com.deu.neyesek.Models.Recipe;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
 * Use the {@link IngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private HomeFragment.OnFragmentInteractionListener mListener;
    String aan = "";
    RecyclerView ingredientRecyclerView ;
    IngredientAdapter ingredientAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    DatabaseReference databaseReference2 ;


    List ingredientlist;
    public IngredientFragment() {
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
    public static IngredientFragment newInstance(String param1, String param2) {
        IngredientFragment fragment = new IngredientFragment();
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
        // Inflate the layout for this fragment

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Handle the back pressed
                View fragmentView2 = inflater.inflate(R.layout.fragment_home, container, false);
                getParentFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment() ).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        View fragmentView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ingredientRecyclerView  = fragmentView.findViewById(R.id.ingredientRV);

        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Ingredient");


        return fragmentView;
    }

    public void onStart() {
        super.onStart();
        final String[] lol = {""};
        // Get List Posts from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ingredientlist = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());
                    Ingredient ingredient = new Ingredient();
                    Map<String, String> map = (Map) postsnap.getValue();
                    ingredient.setIngredientKey(postsnap.getKey());
                    lol[0] =  map.get("Turkish Name");
                    lol[0].replace("\n", "");
                    System.out.println(lol[0]);
                    ingredient.setTurkishName(map.get("Turkish Name").replace("\n", ""));
                    ingredient.setServingSize(map.get("Serving Size") + " GRAM");
                    String xx = map.get("Calorie") + " CAL";
                    System.out.println(xx);
                    ingredient.setCalorie(map.get("Calorie") + " CAL");

                    System.out.println(map.get("Carbohydrates") + "ofamk");
                    ingredient.setCarbohydrates(map.get("Carbohydrates"));
                    ingredient.setCholesterol(map.get("Cholesterol"));
                    ingredient.setEnglishName(map.get("English Name"));
                    ingredient.setSugar(map.get("Sugar"));
                    ingredient.setPotassium(map.get("Potassium"));
                    ingredient.setFat(map.get("Fat"));
                    ingredient.setSaturatedFat(map.get("Saturated Fat"));
                    ingredient.setFiber(map.get("Fiber"));
                    ingredient.setSodium(map.get("Sodium"));
                    ingredient.setProtein(map.get("Protein"));


                    ingredientlist.add(ingredient);


                }
                System.out.println(ingredientlist);
                ingredientAdapter = new IngredientAdapter(getActivity(), ingredientlist);
                ingredientRecyclerView.setAdapter(ingredientAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}