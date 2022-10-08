package forwarderReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Receiver implements Runnable {
    private int port;
    private ServerSocket listener;

    public static JSONObject incommingInfo = null;

    public String receive() throws IOException {
        createServerSocket();
        try (Socket socket = listener.accept()) {
            Scanner in = new Scanner(socket.getInputStream());
            String jsonStr = in.nextLine();
            return jsonStr;
        }

    }

    private void createServerSocket() throws IOException {
        if (listener == null)
            listener = new ServerSocket(port);
    }

    public JSONObject unmarshall(String jsonStr) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonStr);
        JSONObject jObj = (JSONObject) obj;
        return jObj;
    }

    public JSONObject receiveMsg() throws IOException, ParseException {
        String jsonStr = receive();
        return unmarshall(jsonStr);
    }

    public void setPort(int port) throws IOException {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.incommingInfo = receiveMsg();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Finishing receiver thread");
    }

}
