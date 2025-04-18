package com.example.averagecalculator.controller;

import com.example.averagecalculator.service.AverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/numbers")
public class AverageController {

    @Autowired
    private AverageService averageService;

    @GetMapping("/{type}")
    public ResponseEntity<Map<String, Object>> getNumbers(@PathVariable String type) {
        return ResponseEntity.ok(averageService.processRequest(type));
    }
}