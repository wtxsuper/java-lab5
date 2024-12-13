import java.io.*;
import java.util.*;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;
        final String FILE = "clients.txt";

        // Чтение списка клиентов из файла
        List<String> clientAddresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                clientAddresses.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }
        System.out.println("Список клиентов прочитан. Порт сервера " + PORT);
        // Передача сообщений сервером
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите сообщение для отправки клиентам (или '/exit' для выхода):");

            String message;
            while (true) {
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("/exit")) {
                    System.out.println("Сервер завершает работу.");
                    break;
                }
                // Перебираем список клиентов, каждому отправляем сообщение
                for (String address : clientAddresses) {
                    try (Socket socket = new Socket(address, PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        out.println(message);
                        System.out.println("Сообщение отправлено клиенту " + address);
                    } catch (IOException e) {
                        System.out.println("Ошибка отправки сообщения клиенту " + address + ": " + e.getMessage());
                    }
                }

                System.out.println("Введите следующее сообщение (или '/exit' для выхода):");
            }
        }
    }
}
