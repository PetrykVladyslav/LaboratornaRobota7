import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
public class SimpleWebServer {
    private static HttpServer server;
    public static void main(String[] args) throws IOException {
        int port = 8000;
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/classes", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String requestPath = exchange.getRequestURI().getPath();
                if (requestPath.startsWith("/classes/")) {
                    Path filePath = Paths.get(".", requestPath.substring(1));
                    if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                        byte[] fileContent = Files.readAllBytes(filePath);
                        exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
                        exchange.sendResponseHeaders(200, fileContent.length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(fileContent);
                        os.close();
                    } else {
                        exchange.sendResponseHeaders(404, -1);
                    }
                } else {
                    String response = "Простий веб-сервер запущено.";
                    exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                    exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes("UTF-8"));
                    os.close();
                }
            }
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Простий веб-сервер запущений на порту " + port);

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введіть 'exit', щоб закрити веб-сервер.");
            String input;
            do {
                input = scanner.nextLine();
            } while (!input.equals("exit"));

            server.stop(0);
            System.out.println("Простий веб-сервер закрито.");
            scanner.close();
        }).start();
    }
}