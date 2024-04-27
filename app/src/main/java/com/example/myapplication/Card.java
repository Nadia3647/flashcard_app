package com.example.myapplication;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Card implements Serializable {

    private String item1;
    private String item2;
    private int repetitions;
    private float easinessFactor;
    private int interval;
    private int state;
    private int dayCreation;
    private String monthCreation;
    private int yearCreation;
    private int dayNextPractice;
    private String monthNextPractice;
    private int yearNextPractice;

    private String uuid;
    private Boolean isSelected;

    Card (String item1, String item2, int state,  int dayCreation, String monthCreation,
          int yearCreation, int dayNextPractice, String monthNextPractice, int yearNextPractice,
          int repetitions, float easinessFactor, int interval, String uuid){
        this.item1 = item1;
        this.item2 = item2;
        this.state = state;
        this.repetitions = repetitions;
        this.easinessFactor = easinessFactor;
        this.interval = interval;
        this.dayCreation = dayCreation;
        this.monthCreation = monthCreation;
        this.yearCreation = yearCreation;
        this.dayNextPractice = dayNextPractice;
        this.monthNextPractice = monthNextPractice;
        this.yearNextPractice = yearNextPractice;

        this.uuid = uuid;
        this.isSelected = false;
    }

    public void updateParameters(int dayNextPractice, String monthNextPractice, int yearNextPractice,
                                 int repetitions, float easinessFactor, int interval) {
        this.setDayNextPractice(dayNextPractice);
        this.setMonthNextPractice(monthNextPractice);
        this.setYearNextPractice(yearNextPractice);
        this.setRepetitions(repetitions);
        this.setEasinessFactor(easinessFactor);
        this.setInterval(interval);
    }




    // Getters

    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }

    public int getState() {
        return state;
    }

    public int getDayCreation(){return dayCreation;}
    public String getMonthCreation(){return monthCreation;}

    public int getYearCreation() {
        return yearCreation;
    }

    public void setDayCreation(int dayCreation) {
        this.dayCreation = dayCreation;
    }

    public void setMonthCreation(String monthCreation) {
        this.monthCreation = monthCreation;
    }

    public void setYearCreation(int yearCreation) {
        this.yearCreation = yearCreation;
    }

    public void setDayNextPractice(int dayNextPractice) {
        this.dayNextPractice = dayNextPractice;
    }

    public void setMonthNextPractice(String monthNextPractice) {
        this.monthNextPractice = monthNextPractice;
    }

    public void setYearNextPractice(int yearNextPractice) {
        this.yearNextPractice = yearNextPractice;
    }



    public int getRepetitions() {
        return repetitions;
    }

    public float getEasinessFactor() {
        return easinessFactor;
    }

    public int getInterval() {
        return interval;
    }



    public String getUuid() {
        return uuid;
    }


    public Boolean getSelected() {
        return isSelected;
    }

    // Setters

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public void setEasinessFactor(float easinessFactor) {
        this.easinessFactor = easinessFactor;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    public void setState(int state) {
        this.state = state;
    }


    public void setIsSelected(Boolean isSelected) { this.isSelected = isSelected;}
}