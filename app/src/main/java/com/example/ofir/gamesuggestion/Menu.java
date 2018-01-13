package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity
{
    User user = Statics.user;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.menu_layout));

        if (!user.admin)
        {
            ViewGroup layout = (ViewGroup) (findViewById(R.id.show_all_users)).getParent();
            if (null != layout) //for safety only  as you are doing onClick
                layout.removeView(findViewById(R.id.show_all_users));

        }
    }

    public void showUser(View view)
    {
        Intent intent = new Intent(this, ShowUser.class);
        startActivity(intent);
    }

    public void userPanel(View view)
    {
        Intent intent = new Intent(this, UserPanel.class);
        startActivity(intent);
    }

    public void showMap(View view)
    {
        Intent intent = new Intent(this, MapsActivityCurrentPlace.class);
        startActivity(intent);
    }

    public void readme(View view)
    {
        Intent intent = new Intent(this, Readme.class);
        startActivity(intent);
    }

    public void choosesGenre(View view)
    {
        Intent intent = new Intent(this, ChoosesGenre.class);
        startActivity(intent);
    }

    public void showAllUsers(View view)
    {
        Intent intent = new Intent(this, ShowAllUsers.class);
        startActivity(intent);
    }

    public void showGames(View view)
    {
        Intent intent = new Intent(this, ShowGame.class);
        startActivity(intent);
    }

    public void logout(View view)
    {
        finish();
    }
}
