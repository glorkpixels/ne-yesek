package com.deu.neyesek.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.deu.neyesek.Adapters.RecipeAdapter;
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
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppinglistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppinglistFragment extends Fragment {

    String shopl;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button deletethelist ;
    TextView List;
    String temp ="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppinglistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppinglistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppinglistFragment newInstance(String param1, String param2) {
        ShoppinglistFragment fragment = new ShoppinglistFragment();
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        View fragmentView = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        shopl = "";
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserShoppingList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    temp ="";
                    temp = postsnap.getValue(String.class);
                    temp.replace(";"," \n");
                    shopl += temp;
                    System.out.println(postsnap.getValue(String.class));
                }

                List = fragmentView.findViewById(R.id.shoppinglists);
                shopl = shopl.replaceAll(";", "\n");

                if (shopl.equals(""))
                {
                    List.setText("Your shopping list is empty.");
                }
                else{

                    List.setText(shopl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deletethelist = fragmentView.findViewById(R.id.deleteMyList);
        deletethelist.setOnClickListener(v -> {


            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child("UserShoppingList").child(firebaseUser.getUid()).removeValue();



            View fragmentView2 = inflater.inflate(R.layout.fragment_home, container, false);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment() ).commit();
        });


        return fragmentView;
    }

    public void onStart() {
        super.onStart();

        // Get List Posts from the database



    }
}
