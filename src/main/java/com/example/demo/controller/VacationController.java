package com.example.demo.controller;

import com.example.demo.dto.VacationDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.service.VacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> modifyVacation(@RequestBody VacationRequestDto vacationRequestDto) {
        vacationService.modifyVacation(vacationRequestDto);
        return null;
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> cancelVacation(@RequestBody Long historyId) {
        vacationService.cancelVacation(historyId);
        return null;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<VacationDto>> readVacations() {
        return ResponseEntity.ok(vacationService.readVacations());
    }

    @GetMapping("/detail/{historyId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<VacationDto> readVacationDetail(@PathVariable Long historyId) {
        return ResponseEntity.ok(vacationService.readVacation(historyId));
    }

}
