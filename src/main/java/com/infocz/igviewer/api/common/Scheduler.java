package com.infocz.igviewer.api.common;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.infocz.igviewer.api.servive.session.SessionService;

import lombok.extern.slf4j.Slf4j;;

@Component
@Slf4j
public class Scheduler {
  @Autowired SessionService sessionService;
  
  @Scheduled(cron = "0 0/10 * * * ?")
  private void timeoutSession () throws IOException {
    log.info("Scheduler timeoutSession Start >>>>>>>>>>>>>>>>>> {}" , LocalDateTime.now());
    sessionService.timeoutSession();
    log.info("Scheduler timeoutSession End   <<<<<<<<<<<<<<<<<< {}" , LocalDateTime.now());
  }
}
