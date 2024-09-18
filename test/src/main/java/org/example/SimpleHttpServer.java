package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        // Create an HttpServer instance listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define a context for "/hello" which will be handled by HelloHandler
        server.createContext("/hello", new HelloHandler());

        // Set default executor (creates a default thread pool)
        server.setExecutor(null);

        // Start the server
        server.start();
        System.out.println("Server is running on http://localhost:8080/hello");
    }

    // Custom handler for the /hello endpoint
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Define a simple response
            String response = "Hello, World!";

            // Send response headers (200 OK and the length of the response)
            exchange.sendResponseHeaders(200, response.getBytes().length);

            // Write the response to the output stream
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
