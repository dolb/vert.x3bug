package com.test.bug;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author dolb
 */
public class TestVerticle extends AbstractVerticle {

    public void start() {
        vertx.eventBus().consumer("test", this::handle);
    }

    private void handle(Message<JsonObject> msg) {
        System.out.println("Message received: " + msg.body());
        System.out.println(msg.headers()); //This gives an error
        msg.reply(msg.body());
    }
}
