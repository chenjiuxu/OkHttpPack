package chenjiuxu.okhttppack.http.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import chenjiuxu.okhttppack.http.listener.ResultListener;
import okhttp3.ResponseBody;

/**
 * Created by 15705 on 2017/9/18.
 * 文件下载
 */
public class DownloadCallback extends BasicCallback {

    private String savePath;
    private ResultListener listener;

    public DownloadCallback(String savePath, ResultListener listener) {
        this.savePath = savePath;
        this.listener = listener;
    }

    @Override
    public void dataOK(String url, ResponseBody body) {
        File file = initFile(savePath);
        long size = body.contentLength();//获取文件长度
        byte[] buf = new byte[20240];//缓冲数组
        try (InputStream input = body.byteStream();
             FileOutputStream out = new FileOutputStream(file)) {
            float loadSize = 0f;//下载到的长度
            int readSize = 0;//一次读取的字节长度
            while ((readSize = input.read(buf)) != -1) {
                loadSize += readSize;
                out.write(buf, 0, readSize);
                int percent = (int) (loadSize / size * 100);
                listener.onResponse("load", percent);//下载进度
            }
            out.flush();//刷出缓冲
            listener.onResponse("loadOK", file.getPath());

        } catch (IOException e) {
            e.printStackTrace();
            error("load", e);
        }
    }

    @Override
    public void error(String url, Exception e) {
        listener.onFailure("loadNO", e);
    }

    /**
     * 保存文件初始
     */
    private File initFile(String path) {
        File file = new File(path);
        if (file.exists()) {//判断文件是否存在
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }


}
