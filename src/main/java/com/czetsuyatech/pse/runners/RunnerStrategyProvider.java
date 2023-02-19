package com.czetsuyatech.pse.runners;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class RunnerStrategyProvider {

  final DataImporterStrategy dataImporter;
  final DataConsumerStrategy dataConsumer;

  public void run(String runMode) {
    Map<String, RunnerStrategy> runners = new HashMap<>() {{
      put("import", dataImporter);
      put("consumer", dataConsumer);
    }};

    Optional.ofNullable(runners.get(runMode))
        .orElseThrow(() -> new RuntimeException("Unsupported run mode"))
        .run();
  }
}
