package com.deu.neyesek.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deu.neyesek.Adapters.IngredientAdapter;
import com.deu.neyesek.Models.BMI;
import com.deu.neyesek.Models.BMICalcUtil;
import com.deu.neyesek.Models.Ingredient;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.deu.neyesek.Models.BMICalcUtil.BMI_CATEGORY_HEALTHY;
import static com.deu.neyesek.Models.BMICalcUtil.BMI_CATEGORY_OBESE;
import static com.deu.neyesek.Models.BMICalcUtil.BMI_CATEGORY_OVERWEIGHT;
import static com.deu.neyesek.Models.BMICalcUtil.BMI_CATEGORY_UNDERWEIGHT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BMIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BMIFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2 ;
    String userId;
    CardView bmiResultCardView;
    TextView bmiTextView, categoryTextView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BMIFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BMIFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BMIFragment newInstance(String param1, String param2) {
        BMIFragment fragment = new BMIFragment();
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

        View fragmentView = inflater.inflate(R.layout.fragment_b_m_i, container, false);
        bmiTextView = fragmentView.findViewById(R.id.activity_main_bmi);
        categoryTextView = fragmentView.findViewById(R.id.activity_main_category);
        bmiResultCardView = fragmentView.findViewById(R.id.activity_main_resultcard);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase.getReference("User");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());
                    if (firebaseUser.getUid().equals(postsnap.getKey())) {
                        System.out.println("TRUE");
                        Map<String, String> map = (Map) postsnap.getValue();
                        BMI cal = new BMI();
                        cal.setHeight(Double.valueOf(map.get("Height")));
                        cal.setWeight(Double.valueOf(map.get("Weight")));
                        cal.setBmi( BMICalcUtil.getInstance().calculateBMIMetric(cal.getHeight(), cal.getWeight()));
                        displayBMI(cal.getBmi());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_m_i, container, false);
    }

            private void displayBMI(double bmi) {
                bmiResultCardView.setVisibility(View.VISIBLE);

                bmiTextView.setText(String.format("%.2f", bmi));

                String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
                categoryTextView.setText(bmiCategory);

                switch (bmiCategory) {
                    case BMI_CATEGORY_UNDERWEIGHT:
                        bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                        break;
                    case BMI_CATEGORY_HEALTHY:
                        bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                        break;
                    case BMI_CATEGORY_OVERWEIGHT:
                        bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                        break;
                    case BMI_CATEGORY_OBESE:
                        bmiResultCardView.setCardBackgroundColor(Color.RED);
                        break;
                }
            }
}