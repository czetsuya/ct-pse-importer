package com.czetsuyatech.pse.services.pojos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@RegisterForReflection
@Builder
@Getter
@Setter
public class TickerName {

  public final String ticker;

  public TickerName(String ticker) {
    this.ticker = ticker;
  }
}
