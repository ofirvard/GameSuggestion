package com.example.ofir.gamesuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ofir on 1/11/2018.
 */

public class UserAdapter extends ArrayAdapter
{
    ArrayList<User> list;
    Context context;

    public UserAdapter(Context context, ArrayList<User> list)
    {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);

        //username
        ((TextView) convertView.findViewById(R.id.username)).setText(list.get(position).username);
        ((TextView) convertView.findViewById(R.id.username)).setTypeface(Statics.font);

        //password
        ((TextView) convertView.findViewById(R.id.password)).setText(list.get(position).password);
        ((TextView) convertView.findViewById(R.id.password)).setTypeface(Statics.font);

        //admin
        if (list.get(position).admin)
            ((TextView) convertView.findViewById(R.id.admin)).setText(context.getString(R.string.true1));
        else
            ((TextView) convertView.findViewById(R.id.admin)).setText(context.getString(R.string.false1));
        ((TextView) convertView.findViewById(R.id.admin)).setTypeface(Statics.font);

        return convertView;
    }
}
