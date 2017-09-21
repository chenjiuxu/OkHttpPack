package chenjiuxu.okhttppack.http;


import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import chenjiuxu.okhttppack.http.callback.BasicCallback;
import chenjiuxu.okhttppack.http.listener.ProgressListener;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 15705 on 2017/9/13.
 * 封装网络请求的基本方式
 */
public class OkNetwork {
    private static OkHttpClient okHttp;

    private OkNetwork() {

    }

    /**
     * 单利模式
     */
    public static void createOkHttpClient() {
        if (okHttp == null) okHttp = new OkHttpClient();
    }


    //get

    /**
     * 主线程get调用（同步）
     */
    public static void synchGet(String url, HashMap<String, String> params, BasicCallback callback) {
        if (params != null && !params.isEmpty()) url = jointParam(url, params);//参数拼接
        Request request = new Request.Builder().url(url).build();
        Call call = okHttp.newCall(request);
        try (Response response = call.execute()) {
            callback.onResponse(call, response);
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    /**
     * 子线程get调用（异步）
     */
    public static void asynchGet(String url, HashMap<String, String> params, BasicCallback callback) {
        if (params != null && !params.isEmpty()) url = jointParam(url, params);//参数拼接
        Request request = new Request.Builder().url(url).build();
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    //psot

    /**
     * 子线程 post调用(异步)
     */
    public static void asynchPost(String url, HashMap<String, String> params, BasicCallback callback) {
        FormBody formBody = postParam(params);//参数处理
        Request request = new Request.Builder().url(url).post(formBody).build();//封装请求体
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 子线程 post文件参数上传 (异步)
     *
     * @param url       连接
     * @param params    参数列表
     * @param files     文件
     * @param callback  回复结果
     * @param pListener 上传多少
     */
    public static void asynchFilePost(String url, HashMap<String, String> params, HashMap<String, File> files, BasicCallback callback, ProgressListener pListener) {
        MultipartBody multipartBody = postFileParam(params, files, pListener);//参数处理
        Request request = new Request.Builder().url(url).post(multipartBody).build();//封装请求体
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    /**
     * post请求有文件参数
     */
    private static MultipartBody postFileParam(HashMap<String, String> params, HashMap<String, File> files, ProgressListener pListener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (params != null) {//参数
            for (Map.Entry<String, String> entry : params.entrySet())
                builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, File> entry : files.entrySet()) {//文件
            File file = entry.getValue();//获取上传文件
            String fName = file.getName();//获取文件名
            String type = guessMimeType(fName);//根据文件名获取该文件对应的类型
            FileRequestBody fileBody = new FileRequestBody(MediaType.parse(type), file, pListener);//封装文件请求
            builder.addFormDataPart(entry.getKey(), fName, fileBody);//
        }
        return builder.build();
    }

    /**
     * 获取文件HTTP 类型
     */
    private static String guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null) contentTypeFor = "application/octet-stream";
        return contentTypeFor;
    }


    /**
     * post请求无文件参数
     */
    private static FormBody postParam(HashMap<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet())
                builder.addEncoded(entry.getKey(), entry.getValue());
        }
        return builder.build();

    }

    /**
     * 拼接get请求参数
     */
    private static String jointParam(String url, HashMap<String, String> params) {
        StringBuffer path = new StringBuffer(url);
        path.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            path.append(entry.getKey());
            path.append("=");
            path.append(entry.getValue());
            path.append("&");
        }
        path.deleteCharAt(path.length() - 1);
        return path.toString();
    }


}
