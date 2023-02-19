package com.czetsuyatech.pse.factories;

import com.czetsuyatech.pse.services.pojos.TickerName;
import java.util.List;

public interface TickerFactory {

  List<TickerName> getTickerNames();
}
