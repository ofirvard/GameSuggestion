package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Login extends AppCompatActivity
{
    private FirebaseAnalytics mFirebaseAnalytics;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //setup analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //setups the font
        Statics.setFont(this);
        Statics.font((LinearLayout) findViewById(R.id.login_layout));

        ((TextView) findViewById(R.id.version)).setText(BuildConfig.VERSION_NAME);
    }

    public void signIn(View v)
    {
//        if (((EditText) findViewById(R.id.username)).getText() == "")
        final String username = ((EditText) findViewById(R.id.username)).getText().toString(),
                password = ((EditText) findViewById(R.id.password)).getText().toString();
//        test
//        final String username = "ofir", password = "123";
//        test

        if (username.isEmpty() || password.isEmpty())
        {
            ((TextView) findViewById(R.id.error)).setText(getString(R.string.login_error_1));

            //firebase event
            Bundle params = new Bundle();
            params.putString("Username", username);
            params.putString("Password", password);
            params.putString("Success", "false");
            mFirebaseAnalytics.logEvent("login", params);
        }
        else
        {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");

            ValueEventListener login = new ValueEventListener()
            {
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(username).exists() && ((String) dataSnapshot.child(username).child("password").getValue()).equals(password))
                    {
                        DataSnapshot data = dataSnapshot.child(username);
                        Statics.user = new User(data.child("username").getValue().toString(),
                                data.child("password").getValue().toString(),
                                data.child("firstname").getValue().toString(),
                                data.child("lastname").getValue().toString(),
                                data.child("street").getValue().toString(),
                                data.child("email").getValue().toString(),
                                data.child("admin").exists());

                        for (DataSnapshot genre : data.child("genre").getChildren())
                            Statics.user.genres.add((String) genre.getValue());

                        success();
                    }
                    else
                    {
                        ((TextView) findViewById(R.id.error)).setText(getString(R.string.login_error_1));

                        //firebase event
                        Bundle params = new Bundle();
                        params.putString("Username", username);
                        params.putString("Password", password);
                        params.putString("Success", "false");
                        mFirebaseAnalytics.logEvent("login", params);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    Log.e("1", "onCancelled", databaseError.toException());
                }
            };

            database.addListenerForSingleValueEvent(login);
        }
    }

    public void success()
    {
        //firebase event
        Bundle params = new Bundle();
        params.putString("Username", Statics.user.username);
        params.putString("Password", Statics.user.password);
        params.putString("Success", "true");
        mFirebaseAnalytics.logEvent("login", params);

        //intent
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void signUp(View v)
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
