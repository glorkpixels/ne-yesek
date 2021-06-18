package com.deu.neyesek.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deu.neyesek.Helpers.MySingleton;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.Cache;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CheckBox oneMeal, oneDay, threeDay, sevenDay;
    CheckBox breakfast, lunch, dinner;
    CheckBox yestoHome;
    CheckBox yestoPrefrences;
    int m;
    int o;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button recommendMe;
    TextView recommendations;
    View fragmentView ;
    String url = "https://neyesekapi.herokuapp.com/recommendation?";
    private String mParam1;
    private String mParam2;
    private RecommendationFragment.OnFragmentInteractionListener mListener;

    public RecommendationFragment() {
        // Required empty public constructor
    }

    /**https://neyesekapi.herokuapp.com/recommendation?&RecipeKey=-MbO-7NmZ8MNeN8rhIeR
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendationFragment newInstance(String param1, String param2) {
        RecommendationFragment fragment = new RecommendationFragment();
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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fragmentView = inflater.inflate(R.layout.fragment_recommendation, container, false);
        recommendations = fragmentView.findViewById(R.id.xxx);
        recommendMe = fragmentView.findViewById(R.id.recommendme);
        oneMeal = fragmentView.findViewById(R.id.checkBox_tekogun);
        oneDay= fragmentView.findViewById(R.id.checkBox_birgünlük);
        threeDay = fragmentView.findViewById(R.id.checkBox_ücgünlük);
        sevenDay= fragmentView.findViewById(R.id.checkBox_7günlük);;


        breakfast = fragmentView.findViewById(R.id.checkBox_Breakfast);
        lunch= fragmentView.findViewById(R.id.checkBox_Lunch);
        dinner = fragmentView.findViewById(R.id.checkBox_Dinner);

        yestoHome =  fragmentView.findViewById(R.id.checkBox_yes);

        yestoPrefrences =  fragmentView.findViewById(R.id.checkBox_yes_fav);

        RequestQueue queue = MySingleton.getInstance(getActivity().getApplicationContext()).
                getRequestQueue();

        System.out.println("Bax");

        oneMeal.isChecked();

        recommendMe.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                m=0;
                o=0;
                if(oneMeal.isChecked())
                    m++; // you can save this as checked somewhere
                if(oneDay.isChecked())
                    m++; // you can save this as checked somewhere
                if(threeDay.isChecked())
                    m++; // you can save this as checked somewhere
                if(sevenDay.isChecked())
                    m++; // you can save this as checked somewhere

                if(breakfast.isChecked())
                    o++; // you can save this as checked somewhere
                if(lunch.isChecked())
                    o++; // you can save this as checked somewhere
                if(dinner.isChecked())
                    o++; // you can save this as checked somewhere

                if (m == 1 && o>0){
                    System.out.println("Basıldım");

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url,  null, (Response.Listener<JSONObject>) response -> {
                                recommendations.setText("Response: " + response.toString());
                                System.out.println(response.toString());

                                Toast.makeText(getActivity().getBaseContext(), "Recommendation done.", Toast.LENGTH_LONG).show();
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getBaseContext(), "Recommendation service cannot be reached please try again.", Toast.LENGTH_LONG).show();
                                }
                            });
                    MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                }
                else
                {
                    if (m >1){
                        Toast.makeText(getActivity().getBaseContext(), "Lütfen tek öğün miktarı seçin.", Toast.LENGTH_LONG).show();
                    }
                    if (m ==0){
                        Toast.makeText(getActivity().getBaseContext(), "Lütfen öğün miktarı seçin.", Toast.LENGTH_LONG).show();
                    }
                    if (o == 0){
                        Toast.makeText(getActivity().getBaseContext(), "Lütfen istenilen tarif zamanı seçin.", Toast.LENGTH_LONG).show();
                    }

                }



            }
        });




        return fragmentView;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            recommendations = getView().findViewById(R.id.xxx);
            recommendMe = getView().findViewById(R.id.recommendme);


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void onStart() {
        super.onStart();





    }
}