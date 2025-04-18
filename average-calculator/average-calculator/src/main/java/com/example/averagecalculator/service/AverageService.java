package com.example.averagecalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;

@Service
public class AverageService {

    private static final int WINDOW_SIZE = 10;
    private final Deque<Integer> window = new ArrayDeque<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Map<String, String> typeToUrl = Map.of(
            "p", "http://20.244.58.144/evaluation-service/primes",
            "f", "http://20.244.58.144/evaluation-service/fibo",
            "e", "http://20.244.58.144/evaluation-service/even",
            "r", "http://20.244.58.144/evaluation-service/rand"
    );

    public Map<String, Object> processRequest(String type) {
        Map<String, Object> responseMap = new LinkedHashMap<>();

        List<Integer> prevWindow = new ArrayList<>(window);
        List<Integer> fetched = fetchNumbersFromThirdParty(type);

        for (Integer num : fetched) {
            if (!window.contains(num)) {
                if (window.size() >= WINDOW_SIZE) {
                    window.pollFirst();
                }
                window.offerLast(num);
            }
        }

        responseMap.put("windowPrevState", prevWindow);
        responseMap.put("windowCurrState", new ArrayList<>(window));
        responseMap.put("numbers", fetched);

        if (window.size() > 0) {
            double avg = window.stream().mapToInt(i -> i).average().orElse(0);
            responseMap.put("avg", avg);
        } else {
            responseMap.put("avg", 0);
        }

        return responseMap;
    }

    private List<Integer> fetchNumbersFromThirdParty(String type) {
        String url = typeToUrl.get(type);
        if (url == null) return Collections.emptyList();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Integer>> future = executor.submit(() -> {
            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                return (List<Integer>) response.get("numbers");
            } catch (Exception e) {
                return Collections.emptyList();
            }
        });

        try {
            return future.get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            future.cancel(true);
            return Collections.emptyList();
        } finally {
            executor.shutdown();
        }
    }
}
