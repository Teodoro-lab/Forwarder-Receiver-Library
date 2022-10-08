package forwarderReceiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

public class Forwarder {
    private String host = "localhost";
    private int port;

    private String marshall(JSONObject json) {
        // converts JSON obj to string which will be sent
        String jsonStr = json.toJSONString();
        return jsonStr;
    }

    private void deliver(String info) throws UnknownHostException, IOException {
        try (Socket socket = new Socket(host, port)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println(info);
        }
    }

    public void sendMsg(JSONObject infoToSend) throws UnknownHostException, IOException {
        String info = marshall(infoToSend);
        deliver(info);
    }

    public void setPort(int port) {
        this.port = port;
    }

}
