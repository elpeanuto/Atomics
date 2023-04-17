package edu.elpeanuto.lab.task.api;

import edu.elpeanuto.lab.model.Answer;

import java.util.List;

public interface Executable {

    Answer execute(List<Integer> list) throws InterruptedException;
}
