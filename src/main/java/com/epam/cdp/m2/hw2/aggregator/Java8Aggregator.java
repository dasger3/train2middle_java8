package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::valueOf).sum();
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        return words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        return words.stream()
                .collect(Collectors.groupingBy(String::toUpperCase, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(n -> n.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted(listComparator)
                .limit(limit)
                .collect(Collectors.toList());
    }

    Comparator<String> listComparator =
            (e1, e2) -> (e1.length() == e2.length()) ? e1.compareTo(e2) : (e1.length() < e2.length()) ? -1 : 1;
};