package edu.elpeanuto.lab;

import edu.elpeanuto.lab.model.Answer;
import edu.elpeanuto.lab.task.api.Executable;
import edu.elpeanuto.lab.task.impl.AtomicSolution;
import edu.elpeanuto.lab.task.impl.BlockingSolution;
import edu.elpeanuto.lab.task.impl.OneThreadSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.elpeanuto.lab.util.TaskOperations.generateList;

public class App {

    public static final Integer NUM_OF_THREADS = 4;

    public static final List<List<Long>> oneThreadTimeLists = new ArrayList<>();
    public static final List<List<Long>> blockingSolutionLists = new ArrayList<>();
    public static final List<List<Long>> atomicSolutionLists = new ArrayList<>();

    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {
            List<Long> oneThreadTime = new ArrayList<>();
            List<Long> blockingSolution = new ArrayList<>();
            List<Long> atomicSolution = new ArrayList<>();

            for (int j = 0; j < 100_000; j += 10_000) {
                executeAllSolutions(oneThreadTime, blockingSolution, atomicSolution, j);
            }
            oneThreadTimeLists.add(oneThreadTime);
            blockingSolutionLists.add(blockingSolution);
            atomicSolutionLists.add(atomicSolution);
        }

        System.out.println(average(oneThreadTimeLists));
        System.out.println(average(blockingSolutionLists));
        System.out.println(average(atomicSolutionLists));
    }

    public static List<Double> average(List<List<Long>> req) {
        int listSize = req.get(0).size();

        return IntStream.range(0, listSize)
                .mapToObj(i -> req.stream()
                        .mapToLong(list -> list.get(i))
                        .average()
                        .orElse(0.0))
                .toList();
    }

    public static void executeAllSolutions(List<Long> oneThreadTime, List<Long> blockingSolution,
                                           List<Long> atomicSolution, int listSize) {
        List<Integer> list = generateList(listSize, 100);

        oneThreadTime.add(timeMeasurement(new OneThreadSolution(), list));
        blockingSolution.add(timeMeasurement(new BlockingSolution(NUM_OF_THREADS), list));
        atomicSolution.add(timeMeasurement(new AtomicSolution(NUM_OF_THREADS), list));
    }

    public static long timeMeasurement(Executable task, List<Integer> list) {
        long start = System.nanoTime();

        try {
            task.execute(list);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        long end = System.nanoTime();
        return end - start;
    }
}
