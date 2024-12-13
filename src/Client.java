import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final int PORT = 12345; // Порт для получения сообщений от сервера

        // Создаём серверный сокет клиенту, чтобы слушать входящие соединения от рассылающего сервера
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Клиент запущен на порту " + PORT);
            while (true) {
                // Пытаемся поймать соединение от сервера
                // Обворачиваем буферное сообщение в читаемый вид
                try (Socket socket = serverSocket.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message = in.readLine();
                    System.out.println("Получено сообщение: " + message);
                } catch (IOException e) {
                    System.out.println("Ошибка при получении сообщения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании подключения: " + e.getMessage());
        }
    }
}
