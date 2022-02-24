package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::valueOf).sum();
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        List<Pair<String, Long>> result = new LinkedList<>();
        words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((key, value) -> result.add(new Pair<>(key, value)));
        return result.stream().limit(limit).collect(Collectors.toList());
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        List<String> result = new LinkedList<>();
        words.stream()
                .collect(Collectors.groupingBy(String::toUpperCase, Collectors.counting()))
                .entrySet().stream()
                .filter(n -> n.getValue() > 1).forEach(n -> result.add(n.getKey()));
        return result.stream().sorted((e1, e2) ->
                        (e1.length() == e2.length()) ? e1.compareTo(e2) : (e1.length() < e2.length()) ? -1 : 1)
                .limit(limit)
                .collect(Collectors.toList());
    }
}