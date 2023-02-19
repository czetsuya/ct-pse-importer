package com.czetsuyatech.pse.factories;

import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
import com.czetsuyatech.pse.services.pojos.TickerName;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DbTickerFactory implements TickerFactory {

  final CandlestickRepository candlestickRepository;

  @Override
  public List<TickerName> getTickerNames() {
    return candlestickRepository.listUniqueNames();
  }
}
