package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Readme extends AppCompatActivity
{
    User user = Statics.user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.readme));
    }

    public void back(View view)
    {
        finish();
    }
}
