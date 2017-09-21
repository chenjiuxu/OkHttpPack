package chenjiuxu.okhttppack.http.callback;


import java.io.IOException;

import chenjiuxu.okhttppack.http.OkConnect;
import chenjiuxu.okhttppack.http.listener.ResultListener;
import okhttp3.ResponseBody;

/**
 * Created by 15705 on 2017/9/18.
 * json处理解析器
 */
public class JsonCallback extends BasicCallback {

    private ResultListener listener;
    private Class classOfT;

    public JsonCallback(Class classOfT, ResultListener listener) {
        this.classOfT = classOfT;
        this.listener = listener;
    }

    @Override
    public void dataOK(String url, ResponseBody body) {
        try {
            String data = body.string();
            Object pojo = OkConnect.gson.fromJson(data, classOfT);
            listener.onResponse(url, pojo);
        } catch (IOException e) {
            error(url, e);
            e.printStackTrace();

        }
    }

    @Override
    public void error(String url, Exception e) {
        listener.onFailure(url, e);
    }


}
