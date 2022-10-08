package forwarderReceiver;

import org.json.simple.JSONObject;

public abstract class Service implements Runnable {

    private JSONObject info;

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
    }

}
