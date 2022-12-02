package com.czetsuyatech.pse;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.enterprise.context.control.ActivateRequestContext;

@QuarkusMain
public class Application implements QuarkusApplication {

  @ActivateRequestContext
  @Override
  public int run(String... args) throws Exception {

    Quarkus.waitForExit();
    return 0;
  }
}