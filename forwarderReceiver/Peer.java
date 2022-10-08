package forwarderReceiver;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Peer {
    private Forwarder forwarder;
    private Receiver receiver;
    private Service service;

    public Peer(Service service, int portToListen, int portToSend) throws IOException {
        this.service = service;

        this.receiver = new Receiver();
        receiver.setPort(portToListen);

        this.forwarder = new Forwarder();
        forwarder.setPort(portToSend);
    }

    public JSONObject service() throws UnknownHostException, IOException, ParseException {

        Thread serviceThread = new Thread(service);
        serviceThread.start();

        Thread responseThread = new Thread(receiver);
        responseThread.start();

        while (serviceThread.isAlive() && responseThread.isAlive()) {
        }

        if (!responseThread.isAlive()) {
            System.out.println("Response got it!!");
            serviceThread.stop();
            return receiver.incommingInfo;
        }

        if (!serviceThread.isAlive()) {
            System.out.println("Msg being send!!");
            responseThread.stop();
            forwarder.sendMsg(service.getInfo());
        }

        // WARNING: returning null
        return null;
    }

}
