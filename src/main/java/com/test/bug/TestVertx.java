package com.test.bug;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.apex.Router;
import io.vertx.ext.apex.handler.StaticHandler;
import io.vertx.ext.apex.handler.sockjs.BridgeOptions;
import io.vertx.ext.apex.handler.sockjs.PermittedOptions;
import io.vertx.ext.apex.handler.sockjs.SockJSHandler;

/**
 * @author dolb
 */
public class TestVertx {

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        options.setClustered(true);
        Vertx.clusteredVertx(options, vertxHandler -> {
            if (vertxHandler.succeeded()) {
                vertxHandler.result().deployVerticle(new TestVerticle());
            }
        });
        Vertx.clusteredVertx(options, vertxHandler -> {
            if (vertxHandler.succeeded()) {
                HttpServerOptions httpOptions = new HttpServerOptions();
                HttpServer http = vertxHandler.result().createHttpServer(httpOptions);
                SockJSHandler sockJS = SockJSHandler.create(vertxHandler.result());
                BridgeOptions allAccessOptions = new BridgeOptions().addInboundPermitted(new PermittedOptions().setAddressRegex("test"));
                sockJS.bridge(allAccessOptions);
                Router router = Router.router(vertxHandler.result());
                router.route("/eventbus/*").handler(sockJS);
                router.route("/*").handler(StaticHandler.create("webroot").setDirectoryListing(true));
                http.requestHandler(router::accept).listen(8484);
            }
        });
    }
}
