import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(4000)){
            System.out.println("Server Started");

            int counter = 0;
            Scanner reader = new Scanner(System.in);

            ArrayList<File> files = new ArrayList<File>();

            while(true){
                try (Connector connector = new Connector(server)){

                    System.out.println("Client ID: " + counter);
                    counter++;

                    String command = connector.readLine();
                    System.out.println("Command: " + command);

                    switch (command){
                        case "Send file":{
                            files.add(new File(connector.readLine()));

                            if(files.get(files.size()-1).createNewFile()){
                                System.out.println("File created: " + files.get(files.size()-1).getName());
                                connector.writeLine("File created: " + files.get(files.size()-1).getName());
                            }
                            else {
                                System.out.println("File already exists.");
                                connector.writeLine("File already exists.");
                            }
                            break;
                        }
                        case "Get exact file":{

                            boolean file_exits = false;

                            String name = connector.readLine();

                            for (int i = 0; i < files.size(); i++){
                                if(files.get(i).getName().equals(name)){
                                    Scanner myReader = new Scanner(files.get(i));

                                    String text = "";

                                    while (myReader.hasNextLine()) {
                                        String data = myReader.nextLine();
                                        text = text + data;
                                    }
                                    file_exits = true;

                                    connector.writeLine(text);
                                }
                            }

                            if (!file_exits){
                                connector.writeLine("There is no such file!");
                            }
                            break;
                        }
                        case "Get list of files":{

                            String names = "";

                            for (int i = 0; i < files.size(); i++){
                                System.out.println("File name: " + files.get(i).getName() + "\n" +
                                        "Absolute path: " + files.get(i).getAbsolutePath());

                                names = names + files.get(i).getName() + " | ";
                            }
                            connector.writeLine(names);
                            break;
                        }
                        default:{
                            System.out.println("Command is incorrect!");
                            break;
                        }
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
