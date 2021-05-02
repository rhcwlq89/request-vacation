package com.example.demo.config;

import com.example.demo.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledConfig {
    private final VacationService vacationService;

    @Scheduled(cron = "0 1 0 * * *")
    public void startVacation() {
        vacationService.startVacation();
    }

    @Scheduled(cron = "0 0 0 25 12 *")
    public void offerVacation() {
        vacationService.offerVacation();
    }
}
