package com.example.pm.bunkmanager;

/**
 * Created by V. PRAVEEN KUMAR on 3/16/2018.
 */

public class BunkCalculator {
    private float classesConducted;
    private float classesPresent;
    private int attendorBunk = 0;
    private int targetPercentage;
    private float inputcc;
    private float inputcp;
    private float calculatedPercentage;

    BunkCalculator() {
    }

    BunkCalculator(int classesConducted, int classesPresent, int targetPercentage) {
        this.classesConducted = classesConducted;
        this.classesPresent = classesPresent;
        this.targetPercentage = targetPercentage;
        this.inputcc = classesConducted;
        this.inputcp = classesPresent;
    }

    public int validateInputs() {
        int i = 1;
        if (this.classesPresent > this.classesConducted) {
            i = 0;
        }
        if (this.classesConducted > 1000000.0F) {
            i = 0;
        }
        if (this.classesPresent > 1000000.0F) {
            i = 0;
        }
        return i;
    }

    public BunkCalculator setValues(int classesConducted, int classesPresent, int targetPercentage) {
        this.classesPresent = classesPresent;
        this.classesConducted = classesConducted;
        this.targetPercentage = targetPercentage;
        this.inputcp = classesPresent;
        this.inputcc = classesConducted;
        return this;
    }

    public float percentage() {
        this.calculatedPercentage = (100.F * (this.classesPresent / this.classesConducted));
        return this.calculatedPercentage;
    }

    public void reset() {
        this.calculatedPercentage = 0.0F;
        this.attendorBunk = 0;
    }

    public float originalPercentage() {
        return 100.0F * (inputcp / inputcc);
    }

    public int calculate() {
        reset();
        percentage();
        if (percentage() > this.targetPercentage) {
            return this.attendorBunk = (int) (((100*this.classesPresent) - (this.targetPercentage * this.classesConducted)) / (this.targetPercentage));

        }
        if (percentage() == this.targetPercentage) {
            this.attendorBunk = 0;
            return this.attendorBunk;
        }
           return  this.attendorBunk = (int) (((this.targetPercentage * this.classesConducted) - (100 + this.classesConducted)) / (100 - this.targetPercentage));
    }
}
