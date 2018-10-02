package com.namkit.namki.novafolio.MainMonitoring;

/**
 * Created by namki on 2018-03-19.
 */

public class SingerItem {

    String name;
    String state;
    String time;
    int color;

    public SingerItem(String name, String state, String time, int color) {
        this.name = name;
        this.state = state;
        this.time = time;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
