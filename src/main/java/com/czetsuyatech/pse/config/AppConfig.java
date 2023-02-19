package com.czetsuyatech.pse.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import java.util.List;

@StaticInitSafe
@ConfigMapping(prefix = "at")
public interface AppConfig {

  String runMode();

  List<String> cryptoPairs();

  int maxCandles();
}
