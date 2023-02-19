package com.czetsuyatech.pse.factories;

import com.czetsuyatech.pse.config.AppConfig;
import com.czetsuyatech.pse.services.pojos.TickerName;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfigTickerFactory implements TickerFactory {

  final AppConfig appConfig;

  @Override
  public List<TickerName> getTickerNames() {
    return appConfig.cryptoPairs().stream()
        .map(this::getTickerName)
        .collect(Collectors.toList());
  }

  private TickerName getTickerName(String t) {
    return TickerName.builder()
        .ticker(t)
        .build();
  }
}
