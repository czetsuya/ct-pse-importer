package com.czetsuyatech.pse.runners;

import com.czetsuyatech.pse.services.CandlestickImporterService;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DataImporterStrategy implements RunnerStrategy {

  final CandlestickImporterService candlestickImporterService;

  @Override
  public void run() {
    try {
      candlestickImporterService.startImport();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
