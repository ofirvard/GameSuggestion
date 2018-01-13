package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class ShowUser extends AppCompatActivity
{
    User user = Statics.user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.show_user));

        ((TextView) findViewById(R.id.info)).setText(user.toString());

        FirebaseStorage.getInstance().getReference().child("images/" + user.username + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                setImage(uri);
            }

        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(Exception exception)
            {
                // Handle any errors
            }
        });
    }

    private void setImage(Uri uri)
    {
        Glide.with(this)
                .load(uri)
                .into((ImageView) findViewById(R.id.picture));
    }

    public void back(View view)
    {
        finish();
    }
}
