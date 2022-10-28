package com.infocz.igviewer.api.common;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.infocz.igviewer.api.session.SessionService;

import lombok.extern.slf4j.Slf4j;;

@Component
@Slf4j
public class Scheduler {
  @Autowired SessionService sessionService;
  
  @Scheduled(cron = "0 0/10 * * * ?")
  private void removeSession () throws IOException {
    log.info("Scheduler removeSession Start >>>>>>>>>>>>>>>>>> {}" , LocalDateTime.now());
    sessionService.removeSession();
    log.info("Scheduler removeSession End   <<<<<<<<<<<<<<<<<< {}" , LocalDateTime.now());
  }
}
