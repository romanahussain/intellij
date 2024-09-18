package org.example;


import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    public static void main(String[] args) throws IOException {
        // Create an HttpServer instance listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define a context for "/hello" which will be handled by HelloHandler
        server.createContext("/hello", new HelloHandler());

        // Set default executor (creates a default thread pool)
        server.setExecutor(null);

        // Start the server
        server.start();
        logger.info("Server started on port 8080. Access http://localhost:8080/hello");
    }

    // Custom handler for the /hello endpoint
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            logger.info("Received a request: {}", exchange.getRequestMethod());

            // Define a simple response
            String response = "Hello, World!";

            // Log the response message
            logger.debug("Sending response: {}", response);

            // Send response headers (200 OK and the length of the response)
            exchange.sendResponseHeaders(200, response.getBytes().length);

            // Write the response to the output stream
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            logger.info("Response sent successfully");
        }
    }
}
