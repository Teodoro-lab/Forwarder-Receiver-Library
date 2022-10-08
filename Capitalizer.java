
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import forwarderReceiver.Peer;
import forwarderReceiver.Service;

public class Capitalizer extends Service {

    @Override
    public void run() {
        System.out.println("Ingrese la frase: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        str = "{ \"upercasedString:\"\"" + str.toUpperCase() + "\"}";

        JSONParser parser = new JSONParser();

        // WARNING: returning null
        Object obj = null;
        try {
            obj = parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jObj = (JSONObject) obj;

        this.setInfo(jObj);
    }

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 2) {
            System.err.println("Pass the PORTs to connect as the sole command line argument");
            return;
        }

        Capitalizer service = new Capitalizer();
        int portToListen = Integer.parseInt(args[0]);
        int portToSend = Integer.parseInt(args[1]);

        Peer peer = new Peer(service, portToListen, portToSend);
        while (true) {
            JSONObject response = peer.service();
            if (response != null)
                System.out.println(response);
        }

    }

}
