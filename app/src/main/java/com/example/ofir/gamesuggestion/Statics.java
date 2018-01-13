package com.example.ofir.gamesuggestion;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ofir on 1/10/2018.
 */

public class Statics
{
    public static User user;
    public static Typeface font;

    public static void setFont(Context context)
    {
        font = Typeface.createFromAsset(context.getAssets(), "font/pressStartK.ttf");
    }

    public static void font(LinearLayout layout)
    {
        for (int i = 0; i < layout.getChildCount(); i++)
        {
            if (layout.getChildAt(i) instanceof Button)
                ((Button) layout.getChildAt(i)).setTypeface(font);
            if (layout.getChildAt(i) instanceof TextView)
                ((TextView) layout.getChildAt(i)).setTypeface(font);
            if (layout.getChildAt(i) instanceof LinearLayout)
                font((LinearLayout) layout.getChildAt(i));
        }
    }
}
