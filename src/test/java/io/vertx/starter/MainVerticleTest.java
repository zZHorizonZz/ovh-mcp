package io.vertx.starter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class MainVerticleTest {

  private Vertx vertx;

  @BeforeEach
  void setUp(VertxTestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle())
      .onComplete(testContext.succeedingThenComplete());
  }

  @AfterEach
  void tearDown(VertxTestContext testContext) {
    vertx.close().onComplete(testContext.succeedingThenComplete());
  }

  @Test
  void testThatTheServerIsStarted(VertxTestContext testContext) {
    vertx.createHttpClient().request(HttpMethod.GET, 8080, "localhost", "/")
      .compose(req -> req.send())
      .compose(resp -> {
        assertEquals(200, resp.statusCode());
        return resp.body();
      })
      .onComplete(testContext.succeeding(body -> {
        assertTrue(body.length() > 0);
        testContext.completeNow();
      }));
  }
}
