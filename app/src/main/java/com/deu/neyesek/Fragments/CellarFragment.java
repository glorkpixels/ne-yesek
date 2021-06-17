package com.deu.neyesek.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deu.neyesek.Adapters.IngredientAdapter;
import com.deu.neyesek.Models.Ingredient;
import com.deu.neyesek.Models.mList;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CellarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CellarFragment extends Fragment {
    String aan = "";
    RecyclerView recipeRecyclerView ;
    IngredientAdapter recipeAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;

    DatabaseReference databaseReference2 ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String temp;
    List<String> recipeIdList;
    List recipeList;

    private CellarFragment.OnFragmentInteractionListener mListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CellarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CellarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CellarFragment newInstance(String param1, String param2) {
        CellarFragment fragment = new CellarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_cellar, container, false);
        recipeRecyclerView  = fragmentView.findViewById(R.id.ingredientRV);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("UserCellarList").child(firebaseUser.getUid());

        databaseReference2 = firebaseDatabase.getReference("Ingredient");
        temp = "";
        recipeList = new ArrayList<>();
        recipeIdList = new ArrayList<>();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Handle the back pressed
                View fragmentView2 = inflater.inflate(R.layout.fragment_home, container, false);
                getParentFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment() ).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecyclerView.setHasFixedSize(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());


                    mList xd = postsnap.getValue(mList.class);
                    //temp = postsnap.getValue(String.class);
                    //temp += (String) postsnap.getKey()+ ",";

                    //Map<String, String> map = (Map) postsnap.getValue();
                    //mList lol =
                    System.out.println(xd.getmKey());
                    //temp = (String) map.get("mKey");
                    recipeIdList.add(xd.getmKey());

                }

                System.out.println(recipeIdList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        System.out.println(temp + "anan");

        final String[] lol = {""};

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                recipeList = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());
                    if (recipeIdList.contains(postsnap.getKey())) {
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

                        recipeList.add(ingredient);

                    }
                }

                recipeAdapter = new IngredientAdapter(getActivity(), recipeList);
                recipeRecyclerView.setAdapter(recipeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return fragmentView;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}