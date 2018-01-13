package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class UserPanel extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.user_panel));
    }

    public void showUser(View view)
    {
        Intent intent = new Intent(this, ShowUser.class);
        startActivity(intent);
    }

    public void editUser(View view)
    {
        Intent intent = new Intent(this, EditUser.class);
        startActivity(intent);
    }

    public void back(View view)
    {
        finish();
    }
}
