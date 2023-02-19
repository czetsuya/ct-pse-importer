package com.czetsuyatech.pse.runners;

import com.czetsuyatech.pse.services.DataConsumerService;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DataConsumerStrategy implements RunnerStrategy {

  final DataConsumerService dataConsumerService;

  @Override
  public void run() {

    dataConsumerService.start();
  }
}
