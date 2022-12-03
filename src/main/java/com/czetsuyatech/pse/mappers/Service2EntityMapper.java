package com.czetsuyatech.pse.mappers;

import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.services.pojos.Candlestick;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.CDI, unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy =
    NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface Service2EntityMapper {

  CandlestickEntity toCandlestickEntity(Candlestick candlestick);
}
