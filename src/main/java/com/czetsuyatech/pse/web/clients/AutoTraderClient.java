package com.czetsuyatech.pse.web.clients;

import com.czetsuyatech.pse.web.models.CandleV1;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/candles")
@RegisterRestClient(configKey = "autoTrader-api")
public interface AutoTraderClient {

  @POST
  void createCandle(CandleV1 candle);
}
