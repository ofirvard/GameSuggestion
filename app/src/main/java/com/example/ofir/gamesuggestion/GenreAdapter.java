package com.example.ofir.gamesuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by ofir on 12/23/2017.
 */

public class GenreAdapter extends ArrayAdapter
{
    ArrayList<GenreItem> list;

    public GenreAdapter(Context context, ArrayList<GenreItem> list)
    {
        super(context, 0, list);
        this.list = list;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.genre_list_item, parent, false);

        ((TextView) convertView.findViewById(R.id.genre_name)).setText(list.get(position).name);
        ((TextView) convertView.findViewById(R.id.genre_name)).setTypeface(Statics.font);

        ((CheckBox) convertView.findViewById(R.id.genre_checkbox)).setChecked(list.get(position).check);

        final View convertView2 = convertView;

        convertView.findViewById(R.id.genre_checkbox).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                list.get(position).check = (((CheckBox) v)).isChecked();
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CheckBox box = ((CheckBox) convertView2.findViewById(R.id.genre_checkbox));

                box.setChecked(!box.isChecked());

                list.get(position).check = box.isChecked();
            }
        };

        convertView.findViewById(R.id.genre_name).setOnClickListener(clickListener);
        convertView.findViewById(R.id.genre_item).setOnClickListener(clickListener);

        return convertView;
    }
}
