package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowGame extends AppCompatActivity
{
    User user = Statics.user;
    ArrayList<GameItem> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.show_games));

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Games");

        ValueEventListener genreList = new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot game : dataSnapshot.getChildren())
                {
                    boolean skip = false;
                    for (String genre : user.genres)
                    {
                        for (DataSnapshot gameGenre : game.child("Genre").getChildren())
                            if (gameGenre.getValue().toString().equals(genre) || (((String) game.child("Name").getValue())).equals("The Elder Scrolls V: Skyrim"))//everyone should play skyrim
                            {
                                games.add(new GameItem((String) game.child("Name").getValue(),
                                        (String) game.child("Date").getValue(),
                                        (double) game.child("Score").getValue()));
                                skip = true;
                                break;
                            }
                        if (skip)
                            break;
                    }
                    skip = false;
                }

                GameAdapter gameAdapter = new GameAdapter(getApplicationContext(), games);
                ((ListView) findViewById(R.id.game_list)).setAdapter(gameAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.e("1", "onCancelled", databaseError.toException());
            }
        };

        database.addListenerForSingleValueEvent(genreList);
    }

    public void back(View view)
    {
        finish();
    }
}
