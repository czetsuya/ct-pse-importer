package com.czetsuyatech.pse;

import com.czetsuyatech.pse.services.CandlestickImporterService;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.inject.Inject;

@QuarkusMain
public class Application implements QuarkusApplication {

  @Inject
  CandlestickImporterService candlestickImporterService;

  @Override
  public int run(String... args) throws Exception {

    candlestickImporterService.startImport();

    Quarkus.waitForExit();
    return 0;
  }

  public static void main(String[] args) {
    Quarkus.run(Application.class, args);
  }
}