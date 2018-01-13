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
import java.util.HashMap;
import java.util.Map;

public class ChoosesGenre extends AppCompatActivity
{
    User user = Statics.user;
    ArrayList<GenreItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooses_genre);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.choose_genre));

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Genres");

        ValueEventListener genreList = new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot data : dataSnapshot.getChildren())
                    if (user.genres.contains((String) data.getValue()))
                        list.add(new GenreItem((String) data.getValue(), true));
                    else
                        list.add(new GenreItem((String) data.getValue(), false));
                GenreAdapter genreAdapter = new GenreAdapter(getApplicationContext(), list);

                ((ListView) findViewById(R.id.genre_list)).setAdapter(genreAdapter);
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

    public void done(View view)
    {
        user.genres.clear();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users").child(user.username).child("genre");
        database.removeValue();

        int i = 0;
        for (GenreItem item : list)
        {
            if (item.check)
            {
                user.genres.add(item.name);
                database.child("" + i).setValue(item.name);
                i++;
            }
        }

        finish();
    }

}
