package edu.elpeanuto.lab.task.impl;

import edu.elpeanuto.lab.model.Answer;
import edu.elpeanuto.lab.task.abstraction.ParallelSolutionAbstract;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static edu.elpeanuto.lab.util.TaskOperations.minElement;
import static edu.elpeanuto.lab.util.TaskOperations.pairedSum;

public class AtomicSolution extends ParallelSolutionAbstract {

    private final AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicInteger sum = new AtomicInteger(0);

    public AtomicSolution(Integer numOfThreads) {
        super(numOfThreads);
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

        return new Answer(min.intValue(), sum.intValue());
    }

    private class Task implements Runnable {

        private final List<Integer> list;

        public Task(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            min.compareAndSet(min.intValue(), minElement(list));
            sum.getAndAdd(pairedSum(list));
        }
    }
}
