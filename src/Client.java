import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        boolean menu = true;

        while (menu) {
            try (Connector connector = new Connector("127.0.0.1", 4000)) {
                System.out.println("Connected to server");

                Scanner scanner = new Scanner(System.in);


                int command;


                System.out.println("Menu: ");
                System.out.println("[1] Send file");
                System.out.println("[2] Get exact file");
                System.out.println("[3] Get list of files");
                System.out.println("[4] Exit");

                command = scanner.nextInt();

                switch (command) {
                    case 1: {
                        connector.writeLine("Send file");

                        System.out.println("Write name: ");
                        String file_name = scanner.next();
                        connector.writeLine(file_name);

                        System.out.println("Result:");
                        System.out.println(connector.readLine());
                        System.out.println();
                        break;
                    }
                    case 2: {
                        connector.writeLine("Get exact file");

                        System.out.println("Write name: ");
                        String file_name = scanner.next();
                        connector.writeLine(file_name);

                        System.out.println("Result:");
                        System.out.println(connector.readLine());
                        System.out.println();
                        break;
                    }
                    case 3: {
                        connector.writeLine("Get list of files");

                        System.out.println("Result:");
                        System.out.println(connector.readLine());
                        System.out.println();
                        break;
                    }
                    case 4: {
                        menu = false;
                        break;
                    }
                    default: {
                        System.out.println("Command is incorrect!");
                        break;
                    }
                }

                connector.close();

                connector.close();
                System.out.println("Connection was closed");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
