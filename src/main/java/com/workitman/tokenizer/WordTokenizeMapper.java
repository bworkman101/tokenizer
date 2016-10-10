package com.workitman.tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class WordTokenizeMapper {

    public Map<String, Integer> map(String wordFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(wordFilePath))) {

            Map<String, Integer> tokenCount = new HashMap<>();

            while(true) {
                String line = reader.readLine();

                if (line == null) {
                    break;
                }

                line = line.replaceAll("\\p{Punct}", "");
                String[] tokens = line.split("\\s");

                for (String token : tokens) {
                    token = token.toLowerCase();
                    Integer count = tokenCount.get(token);
                    if (count == null) {
                        tokenCount.put(token, 1);
                    } else {
                        tokenCount.put(token, count + 1);
                    }
                }
            }

            return tokenCount;

        } catch (Exception e) {
            throw new IllegalArgumentException("unable to read " + wordFilePath);
        }
    }

}
