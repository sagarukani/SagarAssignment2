package com.example.suworkout.model;

public class WorkoutDetailModel {
    private String name;
    private int duration;
    private double calory;

    public WorkoutDetailModel(String name, int duration, double calory) {
        this.name = name;
        this.duration = duration;
        this.calory = calory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCalory() {
        return calory;
    }

    public void setCalory(double calory) {
        this.calory = calory;
    }
}
