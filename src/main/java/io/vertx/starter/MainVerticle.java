package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello Vert.x!"))
        .listen(8080)
        .onSuccess(server -> {
          System.out.println("HTTP server started on port 8080");
          startPromise.complete();
        })
        .onFailure(startPromise::fail);
  }

}
