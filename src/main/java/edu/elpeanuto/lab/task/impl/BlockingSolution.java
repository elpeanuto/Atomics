package edu.elpeanuto.lab.task.impl;

import edu.elpeanuto.lab.model.Answer;
import edu.elpeanuto.lab.task.abstraction.ParallelSolutionAbstract;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static edu.elpeanuto.lab.util.TaskOperations.minElement;
import static edu.elpeanuto.lab.util.TaskOperations.pairedSum;

public class BlockingSolution extends ParallelSolutionAbstract {

    private Integer min = Integer.MAX_VALUE;
    private Integer sum = 0;

    public BlockingSolution(Integer numOfThreads) {
        super(numOfThreads);
    }

    private synchronized void setMin(Integer value) {
        min = value < min ? value : min;
    }

    private synchronized void addSum(Integer value) {
        sum += value;
    }

    public Answer execute(List<Integer> list) throws InterruptedException {
        if (list.size() < numOfThreads) {
            executor.submit(new Task(list));
        } else {
            IntStream.range(0, numOfThreads)
                    .forEach(i -> executor.submit(new Task(balancer(i, list))));
        }
        executor.shutdown();

        if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS))
            System.err.println("Timeout elapsed before termination");

        return new Answer(min, sum);
    }

    private class Task implements Runnable {

        private final List<Integer> list;

        public Task(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            setMin(minElement(list));
            addSum(pairedSum(list));
        }
    }
}
