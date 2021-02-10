package com.deu.neyesek.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deu.neyesek.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class RegisterActivity extends AppCompatActivity {
    ImageView ImgUserPhoto;
    static int PReqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri ;
    EditText Name, Surname, Email, Password, Gender, Height, Weight, Age;
    Button Register;
    Button backToLogin;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference referenceUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.name);
        Surname = findViewById(R.id.surname);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Gender = findViewById(R.id.gender);
        Height = findViewById(R.id.height);
        Weight = findViewById(R.id.weight);
        Age = findViewById(R.id.age);
        Register = findViewById(R.id.register);
        backToLogin = findViewById(R.id.backToLogin);
        fAuth = FirebaseAuth.getInstance();
        referenceUser = FirebaseDatabase.getInstance().getReference().child("User");

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserDrawer.class));
            finish();

           // if there is no user id on that client
        }

        ImgUserPhoto = findViewById(R.id.regUserPhoto) ;

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // listen register button and make sure its all filled

                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String name = Name.getText().toString();
                final String surname = Surname.getText().toString();
                final String age = Age.getText().toString();
                final String height = Height.getText().toString();
                final String weight = Weight.getText().toString();
                final String gender = Gender.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Please enter an email.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Please enter a password..");
                    return;
                }
                if (password.length() < 8) {
                    Password.setError("Incorrect Form Of A Password.");
                    return;
                }

                if (TextUtils.isEmpty(age)) {
                    Password.setError("Please enter a age..");
                    return;
                }
                if (TextUtils.isEmpty(height)) {
                    Password.setError("Please enter a height..");
                    return;
                }
                if (TextUtils.isEmpty(weight)) {
                    Password.setError("Please enter a weight..");
                    return;
                }
                if (TextUtils.isEmpty(gender)) {
                    Password.setError("Please enter a gender..");
                    return;
                }


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))//buralar eklencek
                {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if clicked create student or teacher on chosen  kind

                            String emailUser = fAuth.getCurrentUser().getEmail();
                            DatabaseReference currentUser = referenceUser.child(emailUser);

                            currentUser.child("UserKey").setValue(emailUser);
                            currentUser.child("Name").setValue(name);
                            currentUser.child("Surname").setValue(surname);
                            currentUser.child("Email").setValue(email);
                            currentUser.child("Password").setValue(password);
                            currentUser.child("Age").setValue(age);
                            currentUser.child("Height").setValue(height);
                            currentUser.child("Weight").setValue(weight);
                            currentUser.child("Gender").setValue(gender);

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "Your account has been created..", Toast.LENGTH_LONG).show();
                            updateUI();


                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                    //you need to fill everything ¯\_(ツ)_/¯
                }
            }

        });


        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));// Login Classına yönlendirilmesi gerek

            }
        });

    }

    // update user photo and name
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        // first we need to upload user photo to firebase storage and get url

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded succesfully
                // now we can get our image url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url


                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profleUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            // user info updated successfully
                                            showMessage("Register Complete");
                                            updateUI();
                                        }

                                    }
                                });

                    }
                });


            }
        });
    }

        private void showMessage(String message) {

            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(homeActivity);
        finish();
        //update ui because register success and login ¯\_(ツ)_/¯

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(RegisterActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgUri);


        }


    }
}