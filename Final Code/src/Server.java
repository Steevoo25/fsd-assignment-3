import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException {
        /*Open the server socket*/
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server is running");
        int clientID = 0;
        Database db = new Database();

        if (!db.establishDBConnection()) {
            System.out.println("DB connection fail, stopping.");
        } else {
            System.out.println("Server is now connected to DB");
        }

        while (true) {//Continuously listen for client requests
            Socket clientSocket = serverSocket.accept();//Accept new connection and create the client socket

            clientID++;//Increment clientId. The clientId is not reassigned once used.

            System.out.println("Client " + clientID + " connected with IP " + clientSocket.getInetAddress().getHostAddress());//Display clientId and IP address:

            ClientHandler clientHandler = new ClientHandler(clientSocket, clientID, db);//Create a new client handler object and start the thread
            new Thread(clientHandler).start();
        }
    }
}

