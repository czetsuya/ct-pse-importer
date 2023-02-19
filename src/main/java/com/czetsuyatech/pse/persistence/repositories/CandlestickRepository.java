package com.czetsuyatech.pse.persistence.repositories;

import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.services.pojos.TickerName;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandlestickRepository implements PanacheRepository<CandlestickEntity> {

  public List<CandlestickEntity> findByTicker(String ticker, LocalDate afterThisDate) {

    Map<String, Object> params = new HashMap<>() {{
      put("ticker", ticker);
      put("afterThisDate", afterThisDate);
    }};

    return find("ticker=:ticker AND createdAt>:afterThisDate",
        Sort.by("createdAt").descending(),
        params)
        .stream()
        .toList();
  }

  public List<TickerName> listUniqueNames() {
    return find("SELECT DISTINCT c.ticker FROM " + CandlestickEntity.class.getName() + " c")
        //    return find("#CandlestickEntity.listUniqueTickers")
        // 2022-12-12 11:06:22,241 ERROR [io.qua.run.Application] (Quarkus Main Thread) Failed to start application (with profile dev): io.quarkus.panache.common.exception.PanacheQueryException: Unable to perform a projection on a named query
        .project(TickerName.class)
        .list();
  }
}
