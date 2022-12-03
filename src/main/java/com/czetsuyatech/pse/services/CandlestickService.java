package com.czetsuyatech.pse.services;

import com.czetsuyatech.pse.mappers.Service2EntityMapper;
import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
import com.czetsuyatech.pse.services.pojos.Candlestick;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class CandlestickService {

  final CandlestickRepository candlestickRepository;

  final Service2EntityMapper service2EntityMapper;

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
