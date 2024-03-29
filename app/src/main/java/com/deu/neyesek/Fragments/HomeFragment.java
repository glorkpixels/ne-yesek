package com.deu.neyesek.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageButton Recipe ;
    ImageButton Menus;
    ImageButton Cellar;
    ImageButton ShopList;
    ImageButton Recommend;


    // putting courses on view to show to student classes that enrolled by course code on main screen
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImageButton recipeButton;

    private HomeFragment.OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        Recipe = fragmentView.findViewById(R.id.recipe);

        Recipe.setOnClickListener(v -> {
            View fragmentView2 = inflater.inflate(R.layout.fragment_recipe, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new RecipeFragment() ).commit();

        });

        Menus = fragmentView.findViewById(R.id.menus);

        Menus.setOnClickListener(v -> {
            View fragmentView2 = inflater.inflate(R.layout.fragment_menu, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new MenuFragment() ).commit();
        });

        Cellar = fragmentView.findViewById(R.id.cellar);

        Cellar.setOnClickListener(v -> {
            View fragmentView2 = inflater.inflate(R.layout.fragment_cellar, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new CellarFragment() ).commit();
        });

        ShopList = fragmentView.findViewById(R.id.shoppinglist);

        ShopList.setOnClickListener(v -> {
            View fragmentView2 = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new ShoppinglistFragment() ).commit();

        });

        Recommend = fragmentView.findViewById(R.id.recommend);

        Recommend.setOnClickListener(v -> {
            View fragmentView2 = inflater.inflate(R.layout.fragment_recommendation, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new RecommendationFragment() ).commit();
        });

        return fragmentView ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            Recipe = getView().findViewById(R.id.recipe);
            Menus = getView().findViewById(R.id.menus);
            Cellar = getView().findViewById(R.id.cellar);
            ShopList = getView().findViewById(R.id.shoppinglist);
            Recommend = getView().findViewById(R.id.recommend);
            //set a onclick listener for when the button gets clicked
            //Start new list activity

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void onStart() {
        super.onStart();





    }
}