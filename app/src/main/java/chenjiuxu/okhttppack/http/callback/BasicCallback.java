package chenjiuxu.okhttppack.http.callback;



import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 15705 on 2017/9/14.
 * 异步监听结果标注类型
 */
public abstract class BasicCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        String url=call.request().url().encodedPath();
        error(url,e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        try (ResponseBody body = response.body()) {
            String url = response.request().url().encodedPath();//没有主接口
            dataOK(url,body);
        }
    }

    public abstract void dataOK(String url, ResponseBody body);//数据获取正常

    public abstract void error(String url, Exception e);//数据错误


}


