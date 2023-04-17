package edu.elpeanuto.lab.task.abstraction;

import edu.elpeanuto.lab.task.api.Executable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ParallelSolutionAbstract implements Executable {

    protected final Integer numOfThreads;
    protected final ExecutorService executor;

    protected ParallelSolutionAbstract(Integer numOfThreads) {
        this.numOfThreads = numOfThreads;
        this.executor = Executors.newFixedThreadPool(numOfThreads);
    }

    protected List<Integer> balancer(int iteration, List<Integer> list) {
        int listSize = list.size();
        int step = listSize / numOfThreads;
        int fromIndex = iteration * step;
        int toIndex = iteration != numOfThreads - 1 ? fromIndex + step : listSize;

        return list.subList(fromIndex, toIndex);
    }
}
