package com.czetsuyatech.pse.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface DateTimeMapper {

  default LocalDate asLocalDate(LocalDateTime date) {

    return Optional.ofNullable(date)
        .map(LocalDateTime::toLocalDate)
        .orElse(null);
  }

  default LocalDateTime asLocalDateTime(LocalDate date) {

    return Optional.ofNullable(date)
        .map(LocalDate::atStartOfDay)
        .orElse(null);
  }
}
