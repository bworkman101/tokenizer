package com.workitman.tokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

    /**
     * Read every filename listed in filePathsFile.
     * Read the contents of those files and tokenize all of the words in them.
     * Returns a list of all the tokenized words and their counts.
     *
     * @param filePathsFile
     */
    public Map<String, Integer> tokenizeFiles(String filePathsFile) {
        try {

            Stream<String> lines = Files.lines(Paths.get(filePathsFile));

            Map<String, Integer> wordsTokenized = lines.parallel()
                    .map(fileName -> new WordTokenizeMapper().map(fileName))
                    .reduce((accumulation, tokenized) -> accumulate(accumulation, tokenized))
                    .orElse(Collections.emptyMap());

            List<Map.Entry<String, Integer>> sortedList = wordsTokenized.entrySet()
                    .stream()
                    .sorted((entryLeft, entryRight) -> entryLeft.getValue().compareTo(entryRight.getValue()) * -1) // sort descending
                    .collect(Collectors.toList());

            LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

            for (Map.Entry<String, Integer> entry: sortedList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            return sortedMap;

        } catch (IOException e) {
            throw new IllegalArgumentException("");
        }
    }

    public Map<String, Integer> accumulate(Map<String, Integer> accumulation, Map<String, Integer> tokenized) {

        for (Map.Entry<String, Integer> entry: tokenized.entrySet()) {

            Integer count = accumulation.get(entry.getKey());

            if (count == null) {
                accumulation.put(entry.getKey(), entry.getValue());
            } else {
                accumulation.put(entry.getKey(), entry.getValue() + count);
            }
        }

        return accumulation;
    }

}
