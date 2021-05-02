package com.example.demo.controller;

import com.example.demo.common.ResponseMessage;
import com.example.demo.dto.SearchDTO;
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
    public ResponseEntity<ResponseMessage> requestVacation(@RequestBody VacationRequestDto vacationRequestDto) {
        return ResponseEntity.ok(new ResponseMessage(vacationService.requestVacation(vacationRequestDto), "success"));
    }

    @PutMapping("/{historyId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ResponseMessage> cancelVacation(@PathVariable Long historyId) {
        return ResponseEntity.ok(new ResponseMessage(vacationService.cancelVacation(historyId), "success"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ResponseMessage> readVacations(@ModelAttribute SearchDTO searchDTO) {
        return ResponseEntity.ok(new ResponseMessage(vacationService.readVacations(searchDTO), "success"));
    }
}
