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

import com.deu.neyesek.Adapters.MenuAdapter;
import com.deu.neyesek.Models.Menu;
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
import java.util.concurrent.CountDownLatch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

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
    MenuAdapter recipeAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    List recipeList;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String temp;
    List<String> recipeIdList;
    List<Menu> menuList;
    DatabaseReference databaseReference2 ;

    DatabaseReference databaseReference3 ;

    DatabaseReference databaseReference4 ;
    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Handle the back pressed
                View fragmentView2 = inflater.inflate(R.layout.fragment_home, container, false);
                getParentFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment() ).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        View fragmentView = inflater.inflate(R.layout.fragment_menu, container, false);
        recipeRecyclerView  = fragmentView.findViewById(R.id.recipeRV);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecyclerView.setHasFixedSize(true);
        recipeList = new ArrayList<>();
        recipeIdList = new ArrayList<>();


        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("UserMenus").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    //System.out.println(postsnap.toString());

                    Menu menu = postsnap.getValue(Menu.class);
                    menuList.add(menu);

                    recipeIdList.add(menu.getBreakfast());
                    recipeIdList.add(menu.getLunch());
                    recipeIdList.add(menu.getDinner());


                }


                for (Menu menu : menuList){

                    if (menu.getBreakfast() != null) {
                        databaseReference2 = firebaseDatabase.getReference("Recipe").child(menu.getBreakfast());

                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Recipe recipe = new Recipe();
                                Map<String, String> map = (Map) dataSnapshot.getValue();;
                                recipe.setRecipeKey(menu.getBreakfast());
                                System.out.println("ananı sikem" + map.get("Name"));
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
                                recipe.setDayc(String.valueOf(menu.getDay()));
                                recipe.setTimeofDay("Breakfast");
                                recipeList.add(recipe);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                    }
                    databaseReference3 = firebaseDatabase.getReference("Recipe").child(menu.getLunch());
                    if (menu.getLunch() != null) {
                        databaseReference3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Recipe recipe2 = new Recipe();
                                Map<String, String> map2 = (Map) dataSnapshot.getValue();
                                recipe2.setRecipeKey(menu.getBreakfast());
                                recipe2.setName(map2.get("Name"));
                                recipe2.setShortDescription(map2.get("ShortDescription"));
                                recipe2.setImage(map2.get("Image"));
                                recipe2.setPrepDetails(map2.get("PrepDetails"));
                                recipe2.setIngridients(map2.get("Ingridients"));
                                recipe2.setCategoryBread(map2.get("CategoryBread"));
                                recipe2.setCuisine(map2.get("Cuisine"));
                                recipe2.setMainCategory(map2.get("Category"));
                                recipe2.setIngridientNames(map2.get("IngridientNames"));
                                recipe2.setKeywords(map2.get("Keywords"));
                                recipe2.setRecipeDetails(map2.get("RecipeDetails"));
                                recipe2.setDayc(String.valueOf(menu.getDay()));
                                recipe2.setTimeofDay("Lunch");
                                recipeList.add(recipe2);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                    }

                    databaseReference4 = firebaseDatabase.getReference("Recipe").child(menu.getDinner());
                    if (menu.getDinner() != null) {

                        databaseReference4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Recipe recipe3 = new Recipe();
                                Map<String, String> map3 = (Map) dataSnapshot.getValue();
                                recipe3.setRecipeKey(menu.getBreakfast());
                                recipe3.setName(map3.get("Name"));
                                recipe3.setShortDescription(map3.get("ShortDescription"));
                                recipe3.setImage(map3.get("Image"));
                                recipe3.setPrepDetails(map3.get("PrepDetails"));
                                recipe3.setIngridients(map3.get("Ingridients"));
                                recipe3.setCategoryBread(map3.get("CategoryBread"));
                                recipe3.setCuisine(map3.get("Cuisine"));
                                recipe3.setMainCategory(map3.get("Category"));
                                recipe3.setIngridientNames(map3.get("IngridientNames"));
                                recipe3.setKeywords(map3.get("Keywords"));
                                recipe3.setRecipeDetails(map3.get("RecipeDetails"));
                                recipe3.setDayc(String.valueOf(menu.getDay()));
                                recipe3.setTimeofDay("Dinner");
                                recipeList.add(recipe3);


                                recipeAdapter = new MenuAdapter(getActivity(), recipeList);
                                recipeRecyclerView.setAdapter(recipeAdapter);
                                System.out.println(menuList);
                                System.out.println(recipeIdList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });

                    }

                    temp = "";

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        System.out.println(recipeList.size());


        // Inflate the layout for this fragment
        return fragmentView;
    }
    public void getRecipesPlease(UserListCallback myCallBack){



    }
}



interface UserListCallback {
    void onCallback(List<Recipe> value);
}