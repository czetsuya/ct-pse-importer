package com.czetsuyatech.pse.factories;

import com.czetsuyatech.pse.config.AppConfig;
import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.properties.IfBuildProperty;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class TickerFactoryBeanConfig {

  @Produces
  @DefaultBean
  TickerFactory configTickerFactory(AppConfig appConfig) {
    return new ConfigTickerFactory(appConfig);
  }

  @Produces
  @IfBuildProperty(name = "ticker-source", stringValue = "db")
  TickerFactory dbTickerFactory(CandlestickRepository candlestickRepository) {
    return new DbTickerFactory(candlestickRepository);
  }
}
