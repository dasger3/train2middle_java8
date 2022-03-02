package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Java7Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum;
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        List<Pair<String, Long>> result;
        Map<String, Long> resultMap = new HashMap<>();
        for (String word : words) {
            if (resultMap.containsKey(word)) {
                Long size = resultMap.get(word) + 1;
                resultMap.put(word, size);
            } else {
                resultMap.put(word, 1L);
            }
        }
        result = convertMapToList(resultMap);
        Collections.sort(result, pairComparator);
        return (limit < result.size()) ? result.subList(0, (int)limit) : result;
    }

    Comparator<Pair<String, Long>> pairComparator = new Comparator<Pair<String, Long>>() {
        @Override
        public int compare(Pair<String, Long> e1, Pair<String, Long> e2) {
            Long v1 = e1.getValue();
            Long v2 = e2.getValue();
            if (Objects.equals(v1, v2)) {
                return e1.getKey().compareTo(e2.getKey());
            }
            return v2.compareTo(v1);
        }
    };

    public List<Pair<String, Long>> convertMapToList(Map<String, Long> entries) {
        List<Pair<String, Long>> result = new LinkedList<>();
        Pair<String, Long> tmp;
        for (Map.Entry<String, Long> entry: entries.entrySet()) {
             tmp = new Pair<>(entry.getKey(), entry.getValue());
             result.add(tmp);
        }
        return result;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        List<String> tmp = new LinkedList<>();
        List<String> result = new LinkedList<>();
        for (String word : words) {
            word = word.toUpperCase();
           if (tmp.contains(word)) {
               result.add(word);
           } else {
               tmp.add(word);
           }
        }
        Collections.sort(result, listComparator);
        return (limit < result.size()) ? result.subList(0, (int)limit) : result;
    }

    Comparator<String> listComparator = new Comparator<String>() {
        @Override
        public int compare(String e1, String e2) {
            return (e1.length() == e2.length()) ? e1.compareTo(e2) : (e1.length() < e2.length()) ? -1 : 1;
        }
    };
}
