package chenjiuxu.okhttppack.http.listener;

/**
 * Created by 15705 on 2017/9/18.
 */
public interface ResultListener {

    void onResponse(String url, Object data);//数据获取正常

    void onFailure(String url, Exception e);//数据错误

}
