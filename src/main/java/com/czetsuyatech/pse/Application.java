package com.czetsuyatech.pse;

import com.czetsuyatech.pse.config.AppConfig;
import com.czetsuyatech.pse.runners.RunnerStrategyProvider;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.RequiredArgsConstructor;

@QuarkusMain
@RequiredArgsConstructor
public class Application implements QuarkusApplication {

  final RunnerStrategyProvider runnerStrategyProvider;
  final AppConfig appConfig;

  @Override
  public int run(String... args) {

    runnerStrategyProvider.run(appConfig.runMode());

    Quarkus.waitForExit();

    return 0;
  }

  public static void main(String[] args) {
    Quarkus.run(Application.class, args);
  }
}
