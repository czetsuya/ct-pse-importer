package com.czetsuyatech.pse.persistence.repositories;

import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandlestickRepository implements PanacheRepository<CandlestickEntity> {

}
