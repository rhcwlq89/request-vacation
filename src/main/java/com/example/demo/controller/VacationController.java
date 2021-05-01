package com.example.demo.controller;

import com.example.demo.service.VacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacation")
@Slf4j
public class VacationController {
    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @PostMapping
    public ResponseEntity<String> requestVacation() {
        log.info("requestVacation");
        return null;
    }

    @PutMapping
    public ResponseEntity<String> modifyVacation() {
        log.info("modifyVacation");
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> cancelVacation() {
        log.info("cancelVacation");
        return null;
    }

    @GetMapping
    public ResponseEntity<String> readVacations() {
        log.info("readVacations");
        return null;
    }

    @GetMapping("/detail")
    public ResponseEntity<String> readVacationDetail() {
        return null;
    }

}
