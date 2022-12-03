package com.czetsuyatech.pse.services.pojos;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Candlestick {

  private LocalDate createdAt;

  private String ticker;

  private double open;

  private double close;

  private double high;

  private double low;

  private double volume;
}