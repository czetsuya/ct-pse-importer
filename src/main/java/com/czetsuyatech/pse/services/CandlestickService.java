package com.czetsuyatech.pse.services;

import com.czetsuyatech.pse.config.AppConfig;
import com.czetsuyatech.pse.mappers.CandlestickMapper;
import com.czetsuyatech.pse.mappers.Service2EntityMapper;
import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
import com.czetsuyatech.pse.services.pojos.Candlestick;
import com.czetsuyatech.pse.web.clients.AutoTraderClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class CandlestickService {

  final CandlestickRepository candlestickRepository;
  final Service2EntityMapper service2EntityMapper;
  @RestClient
  AutoTraderClient autoTraderClient;
  final CandlestickMapper candlestickMapper;
  final AppConfig appConfig;

  @Transactional(TxType.REQUIRES_NEW)
  public void processCandlesticks(String ticker, List<Candlestick> candlesticks) {

    Optional.ofNullable(candlesticks)
        .filter(cs -> cs.size() > appConfig.maxCandles())
        .ifPresent(c -> {
          log.info("Processing ticker={}, with {} candles", ticker, candlesticks.size());
          candlesticks.forEach(this::processCandlestick);
        });
  }

  public void processCandlestick(Candlestick candle) {
    autoTraderClient.createCandle(candlestickMapper.asCandlestick(candle));
  }

  @Transactional(TxType.REQUIRES_NEW)
  public void create(Candlestick candlestick) {

    Map<String, Object> params = new HashMap<>();
    params.put("ticker", candlestick.getTicker());
    params.put("createdAt", candlestick.getCreatedAt());

    candlestickRepository.find("ticker=:ticker AND createdAt=:createdAt", params)
        .firstResultOptional()
        .ifPresentOrElse(
            e -> {
            },
            () -> {
              create(service2EntityMapper.toCandlestickEntity(candlestick));
            }
        );
  }

  private void create(CandlestickEntity candlestick) {

    candlestickRepository.persist(candlestick);
  }
}
