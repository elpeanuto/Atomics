package edu.elpeanuto.lab.task.impl;

import edu.elpeanuto.lab.model.Answer;
import edu.elpeanuto.lab.task.api.Executable;

import java.util.List;

import static edu.elpeanuto.lab.util.TaskOperations.minElement;
import static edu.elpeanuto.lab.util.TaskOperations.pairedSum;

public class OneThreadSolution implements Executable {

    public Answer execute(List<Integer> list) {
        return new Answer(minElement(list), pairedSum(list));
    }
}
