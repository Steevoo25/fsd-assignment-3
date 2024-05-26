import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    final Socket clientSocket;
    final int clientNo;
    final Database db;

    //declare variables
    //Constructor
    public ClientHandler(Socket socket, int clientId, Database db) {
        clientSocket = socket;
        clientNo = clientId;
        this.db = db;
    }

    public void run() {
        System.out.println("ClientHandler started...");
        //Create I/O streams to read/write data, PrintWriter and BufferedReader
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;

            //Receive messages from the client and send replies, until the user types "stop"
            int titlesNum;
            while(!(clientMessage = inFromClient.readLine()).equals("stop")){
                System.out.println("Client sent the artist name " + clientMessage);
                titlesNum = db.getTitles(clientMessage);

                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            System.out.println("Client " + clientNo + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");
            //Close I/O streams and socket*/
            inFromClient.close();
            outToClient.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
