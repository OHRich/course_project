import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private UserDatabase userDatabase;

    public ClientHandler(Socket clientSocket, UserDatabase userDatabase) {
        this.clientSocket = clientSocket;
        this.userDatabase = userDatabase;
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request = in.readLine();
        String[] parts = request.split("/");
        if (parts.length >= 3) {
            String operation = parts[0];
            String username = parts[1];
            String password = parts[2];
            boolean result = false;

            if ("login".equalsIgnoreCase(operation)) {
                result = userDatabase.login(username, password);
                if (result) {
                    int level = userDatabase.getLevel(username);
                    out.println("success/" + level);
                } else {
                    out.println("error");
                }
            } else if ("signup".equalsIgnoreCase(operation)) {
                result = userDatabase.signUp(username, password);
                if (result) {
                    out.println("success");
                } else {
                    out.println("error");
                }
            } else if ("levelup".equalsIgnoreCase(operation) && parts.length == 4) {
                int level = Integer.parseInt(parts[3]);
                result = userDatabase.levelUp(username, password, level);
                if (result) {
                    out.println("success");
                } else {
                    out.println("error");
                }
            } else {
                out.println("error");
            }
        } else {
            out.println("error");
        }

        in.close();
        out.close();
    }
}
