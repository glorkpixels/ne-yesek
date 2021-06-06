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

import com.deu.neyesek.Models.BMI;
import com.deu.neyesek.Models.BMICalcUtil;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

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
    public static final String BMI_CATEGORY_UNDERWEIGHT = "Underweight";
    public static final String BMI_CATEGORY_HEALTHY = "Healthy Weight Range";
    public static final String BMI_CATEGORY_OVERWEIGHT = "Overweight";
    public static final String BMI_CATEGORY_OBESE = "Obese";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2 ;
    String userId;
    BMI cal = new BMI();
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


        displayBMI(cal.getBmi());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_bmifrag, container, false);
        bmiTextView = fragmentView.findViewById(R.id.bmires);
        categoryTextView = fragmentView.findViewById(R.id.category);
        bmiResultCardView = fragmentView.findViewById(R.id.result);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase.getReference("User");

        bmiTextView.setText("OF");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());
                    System.out.println(postsnap.getKey());
                    System.out.println(firebaseUser.getUid());
                    if (firebaseUser.getUid().equals(postsnap.getKey())) {
                        System.out.println("TRUE");
                        Map<String, String> map = (Map) postsnap.getValue();
                        cal.setHeight(Double.parseDouble(map.get("Height")));
                        cal.setWeight(Double.parseDouble(map.get("Weight")));
                        System.out.println(cal.getHeight());
                        System.out.println(cal.getWeight());
                        cal.setBmi( BMICalcUtil.getInstance().calculateBMIMetric(cal.getHeight(), cal.getWeight()));

                        System.out.println(cal.getBmi());

                        displayBMI(BMICalcUtil.getInstance().calculateBMIMetric(cal.getHeight(), cal.getWeight()));

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayBMI(cal.getBmi());

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bmifrag, container, false);
    }

            public void displayBMI(double bmi) {
                bmiTextView.setText(String.format("%.2f", bmi));
                System.out.println(String.format("%.2f", bmi));
                String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
                System.out.println(bmiCategory);
                categoryTextView.setText(bmiCategory);

                switch (bmiCategory) {
                    case BMI_CATEGORY_UNDERWEIGHT:
                        bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                        break;
                    case BMI_CATEGORY_HEALTHY:
                        bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                        break;
                    case BMI_CATEGORY_OVERWEIGHT:
                        bmiResultCardView.setCardBackgroundColor(Color.BLUE);
                        break;
                    case BMI_CATEGORY_OBESE:
                        System.out.println("burası");
                        bmiResultCardView.setCardBackgroundColor(Color.RED);
                        break;
                }
            }
}