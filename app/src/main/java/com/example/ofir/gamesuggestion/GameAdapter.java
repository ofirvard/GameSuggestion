package com.example.ofir.gamesuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ofir on 1/5/2018.
 */

public class GameAdapter extends ArrayAdapter
{
    ArrayList<GameItem> list;

    public GameAdapter(Context context, ArrayList<GameItem> list)
    {
        super(context, 0, list);
        this.list = list;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_item, parent, false);

        ((TextView) convertView.findViewById(R.id.game_name)).setText(list.get(position).toString());
        ((TextView) convertView.findViewById(R.id.game_name)).setTypeface(Statics.font);

        return convertView;
    }
}
