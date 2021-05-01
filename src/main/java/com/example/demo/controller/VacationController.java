package com.example.demo.controller;

import com.example.demo.dto.VacationRequestDto;
import com.example.demo.service.VacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> requestVacation(@RequestBody VacationRequestDto vacationRequestDto) {
        vacationService.requestVacation(vacationRequestDto);
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> modifyVacation() {
        log.info("modifyVacation");
        return null;
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> cancelVacation() {
        log.info("cancelVacation");
        return null;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> readVacations() {
        log.info("readVacations");
        return null;
    }

    @GetMapping("/detail")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> readVacationDetail() {
        return null;
    }

}
