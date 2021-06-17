package com.deu.neyesek.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.deu.neyesek.Fragments.BMIFragment;
import com.deu.neyesek.Fragments.FavoriteFragment;
import com.deu.neyesek.Fragments.HomeFragment;
import com.deu.neyesek.Fragments.IngredientFragment;
import com.deu.neyesek.Fragments.RecipeFragment;
import com.deu.neyesek.R;
import com.firebase.ui.auth.data.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.deu.neyesek.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "StudentDrawer";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    SearchView simpleSearchView ;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        HomeFragment.OnFragmentInteractionListener mListener;
        setContentView(R.layout.activity_user_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        updateNavHeader();

        simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);


        getSupportActionBar().setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.HomeFragment) {
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        }
        else  if (id == R.id.RecipeFragment){
            getSupportActionBar().setTitle("Recipes");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new RecipeFragment()).commit();
        }
        else  if (id == R.id.IngredientFragment){
            getSupportActionBar().setTitle("Ingredients");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new IngredientFragment()).commit();
        }
        else if(id == R.id.FavFragment){
            getSupportActionBar().setTitle("Favorites");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
        }
        else if(id == R.id.PreFragment){
            getSupportActionBar().setTitle("Preferences");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
        }
        else if (id == R.id.Signout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(UserDrawer.this, LoginActivity.class);
            startActivity(i);
            finish();


            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView student_name = headerView.findViewById(R.id.username);
        TextView student_mail = headerView.findViewById(R.id.useremail);
        ImageView student_photo = headerView.findViewById(R.id.userimage);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid()).child("Name").getRef();
        System.out.println(databaseReference.getKey()+ "dneme");

       student_name.setText(firebaseUser.getDisplayName());
       student_mail.setText(firebaseUser.getEmail());

        // drawer user credentials setting from database pulling current users info
    }
}