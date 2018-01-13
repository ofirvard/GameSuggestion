package com.example.ofir.gamesuggestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAllUsers extends AppCompatActivity
{
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);

        //setups the font
        Statics.font((LinearLayout) findViewById(R.id.show_all_users));

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");

        ValueEventListener showAll = new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                ArrayList<User> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren())
                    list.add(new User(data.child("username").getValue().toString(), data.child("password").getValue().toString(), data.child("admin").exists()));
                setList(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.e("1", "onCancelled", databaseError.toException());
            }
        };

        database.addListenerForSingleValueEvent(showAll);
    }

    private void setList(ArrayList<User> list)
    {
        UserAdapter userAdapter = new UserAdapter(this, list);
        ((ListView) findViewById(R.id.all_users_list)).setAdapter(userAdapter);
    }

    public void back(View view)
    {
        finish();
    }
}
