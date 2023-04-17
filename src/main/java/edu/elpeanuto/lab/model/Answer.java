package edu.elpeanuto.lab.model;

public record Answer(Integer min, Integer sum) {

    @Override
    public String toString() {
        return "Answer{" +
                "min=" + min +
                ", sum=" + sum +
                '}';
    }
}
