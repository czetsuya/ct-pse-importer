package com.czetsuyatech.pse.web.models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandleV1 {

  private LocalDateTime createdAt;

  private String ticker;

  private double open;

  private double close;

  private double high;

  private double low;

  private double volume;
}
