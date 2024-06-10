package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.model.Card;

import java.time.LocalDate;
import java.time.ZoneId;

public class MemoAlgo {
    public static void SuperMemo2(Card card, int quality) {
        if (quality < 0 || quality > 5) {
            // throw error here or ensure elsewhere that quality is always within 0-5
        }
        // retrieve the stored values
        int repetitions = card.getRepetitions();
        float easiness = card.getEasinessFactor();
        int interval = card.getInterval();
        // easiness factor
        easiness = (float) Math.max(1.3, easiness + 0.1 - (5.0 - quality) * (0.08 + (5.0 - quality) * 0.02));
        // repetitions
        if (quality < 3) {
            repetitions = 0;
        } else {
            repetitions += 1;
        }
        // interval
        if (repetitions <= 1) {
            interval = 1;
        } else if (repetitions == 2) {
            interval = 6;
        } else {
            interval = Math.round(interval * easiness);
        }

        // next practice
        long millisecondsInDay = 60L * 60 * 24 * 1000;
        long now = System.currentTimeMillis();
        long nextPracticeTime = now + millisecondsInDay*interval;
        LocalDate nextPracticeDate = toDate(nextPracticeTime);
        // Store the nextPracticeDate in the database;
         card.updateParameters(nextPracticeDate.getDayOfMonth(), nextPracticeDate.getMonthValue(), nextPracticeDate.getYear(), repetitions, easiness, interval);
    }
    public static LocalDate toDate(long nextPracticeTime) {
        LocalDate nextPracticeDate = new java.util.Date(nextPracticeTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (nextPracticeDate);
    }
}
