package edu.elpeanuto.lab.util;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TaskOperations {

    private static final Random random = new Random();

    public static Integer minElement(List<Integer> list) {
        return list.stream()
                .min(Integer::compareTo)
                .orElse(null);
    }

    public static Integer pairedSum(List<Integer> list) {
        return list.stream()
                .filter(el -> el % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static List<Integer> generateList(int size, int bound) {
        return IntStream
                .generate(() -> random.nextInt(bound))
                .limit(size)
                .boxed()
                .toList();
    }
}
