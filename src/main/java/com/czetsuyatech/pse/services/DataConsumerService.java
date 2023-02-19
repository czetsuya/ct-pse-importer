package com.czetsuyatech.pse.services;

import com.czetsuyatech.pse.config.AppConfig;
import com.czetsuyatech.pse.factories.TickerFactory;
import com.czetsuyatech.pse.mappers.CandlestickMapper;
import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
import com.czetsuyatech.pse.services.pojos.TickerName;
import com.czetsuyatech.pse.web.clients.AutoTraderClient;
import io.quarkus.runtime.Quarkus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DataConsumerService {

  final AppConfig appConfig;
  final TickerFactory tickerFactory;
  final CandlestickRepository candlestickRepository;
  final CandlestickMapper candlestickMapper;
  final CandlestickService candlestickService;

  @Transactional(TxType.NOT_SUPPORTED)
  public void start() {

    log.info("Start processing stock codes");

    List<TickerName> tickerNames = tickerFactory.getTickerNames();

    log.info("Running calculations on {} stocks", tickerNames.size());

    tickerNames.stream()
        .filter(e -> !e.ticker.contains("^"))
        .filter(e -> !e.ticker.equals("-"))
        .forEach(e -> {

          String ticker = e.ticker;

          var candleEntities = getCandlesticks(ticker);
          List<CandlestickEntity> candlesticks = reverse(candleEntities);
          candlestickService.processCandlesticks(ticker, candlestickMapper.asCandlesticks(candlesticks));
        });

    log.info("End processing stock codes");

    Quarkus.asyncExit();
  }

  public <T> List<T> reverse(List<T> unModListOrig) {
    List<T> tmpList = new ArrayList<T>(unModListOrig);
    Collections.reverse(tmpList);

    return Collections.unmodifiableList(tmpList);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public List<CandlestickEntity> getCandlesticks(String ticker) {

    int afterDate = 730; // 2 year
    return candlestickRepository.findByTicker(ticker, LocalDate.now().minusDays(afterDate));
  }
}
