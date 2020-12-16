package com.himanshu.springboot.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public class NumberGenerator implements Runnable {
    private final int goals;
    private final int steps;
    private List<Integer> result;

    NumberGenerator(int goals, int steps) {
        this.goals = goals;
        this.steps = steps;
        this.result = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            for (int i = goals; i >= 0; i-=steps)
                result.add(i);
            if(goals % steps != 0)
                result.add(0);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void generateNumbers() {
        for (int i = goals; i >= 0; i-=steps)
            result.add(i);
        if(goals % steps != 0)
            result.add(0);
    }

    public int getGoals() {
        return goals;
    }

    public int getSteps() {
        return steps;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }
}
