package augusto.hernandez.messagesfx.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class NetworkService extends Service<String> {

    private final String url;
    private final String data;
    private final String method;

    private NetworkService(String url, String data, String method) {
        this.url = url;
        this.data = data;
        this.method = method;
    }

    public static NetworkService createService(String url, String data, String method) {
        return new NetworkService(url, data, method);
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return ServiceUtils.getResponse(url, data, method);
            }
        };
    }
}

