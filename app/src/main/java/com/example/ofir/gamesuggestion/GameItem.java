package com.example.ofir.gamesuggestion;

/**
 * Created by ofir on 1/5/2018.
 */

public class GameItem
{
    String name, date;
    double score;

    public GameItem(String name, String date, double score)
    {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return name + '\n' +
                date + '\n' +
                "Score:" + score + '\n';
    }
}
