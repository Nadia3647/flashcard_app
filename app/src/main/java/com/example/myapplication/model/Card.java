package com.example.myapplication.model;
import java.io.Serializable;
public class Card implements Serializable {
    private String item1;
    private String item2;
    private int repetitions;
    private float easinessFactor;
    private int interval;
    private int dayCreation;
    private int monthCreation;
    private int yearCreation;
    private int dayNextPractice;
    private int monthNextPractice;
    private int yearNextPractice;
    private Integer uuid;
    public Card(){

    }

    public Card (String item1, String item2, int dayCreation, int monthCreation,
          int yearCreation, int dayNextPractice, int monthNextPractice, int yearNextPractice,
          int repetitions, float easinessFactor, int interval, Integer uuid){
        this.item1 = item1;
        this.item2 = item2;
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
    }

    public void updateParameters(int dayNextPractice, int monthNextPractice, int yearNextPractice,
                                 int repetitions, float easinessFactor, int interval) {
        this.setDayNextPractice(dayNextPractice);
        this.setMonthNextPractice(monthNextPractice);
        this.setYearNextPractice(yearNextPractice);
        this.setRepetitions(repetitions);
        this.setEasinessFactor(easinessFactor);
        this.setInterval(interval);
    }
    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }
    public int getDayCreation(){return dayCreation;}
    public int getMonthCreation(){return monthCreation;}

    public int getYearCreation() {
        return yearCreation;
    }

    public void setDayCreation(int dayCreation) {
        this.dayCreation = dayCreation;
    }

    public void setMonthCreation(int monthCreation) {
        this.monthCreation = monthCreation;
    }

    public void setYearCreation(int yearCreation) {
        this.yearCreation = yearCreation;
    }

    public void setDayNextPractice(int dayNextPractice) {
        this.dayNextPractice = dayNextPractice;
    }

    public void setMonthNextPractice(int monthNextPractice) {
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



    public Integer getUuid() {
        return uuid;
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
    public int getDayNextPractice() {
        return dayNextPractice;
    }

    public int getMonthNextPractice() {
        return monthNextPractice;
    }

    public int getYearNextPractice() {
        return yearNextPractice;
    }
}