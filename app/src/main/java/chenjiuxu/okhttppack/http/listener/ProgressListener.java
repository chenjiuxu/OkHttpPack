package chenjiuxu.okhttppack.http.listener;

/**
 * Created by 15705 on 2017/9/20.
 * 上传文件字节监听
 */
public interface ProgressListener {


    /**
     * 处理的字节
     *
     * @param byteCount 处理字节数
     */
    void onProgressListener(long byteCount);
}
