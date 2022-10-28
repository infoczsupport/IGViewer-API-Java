package com.infocz.igviewer.api.batch;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.infocz.igviewer.api.service.SessionService;

import lombok.extern.slf4j.Slf4j;;

@Component
@Slf4j
public class Scheduler {
  @Autowired SessionService sessionService;
  
  @Scheduled(cron = "0 0/5 * * * ?")
  private void deleteSession () throws IOException {
    log.debug("Scheduler DeleteSession Start: {}" , LocalDate.now());
    sessionService.timeoutSession();
    log.debug("Scheduler DeleteSession End: {}" , LocalDate.now());
  }
}
