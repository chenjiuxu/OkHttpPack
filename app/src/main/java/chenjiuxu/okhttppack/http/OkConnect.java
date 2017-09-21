package chenjiuxu.okhttppack.http;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import chenjiuxu.okhttppack.http.callback.DownloadCallback;
import chenjiuxu.okhttppack.http.callback.JsonCallback;
import chenjiuxu.okhttppack.http.listener.ProgressListener;
import chenjiuxu.okhttppack.http.listener.ResultListener;

/**
 * Created by 15705 on 2017/9/18.
 * 序列化网络数据 已经根据业务的基本处理
 */
public class OkConnect {

    private static boolean isInit = false;
    private static String mainUrl;
    public static Gson gson = null;

    /**
     * 初始化必要参数
     */
    private static void inttOkConnect(InitOK init) {
        if (isInit) return;//限制只能初始化一次
        OkNetwork.createOkHttpClient();
        if (init.isGosn) gson = new Gson();
        mainUrl = init.mainUrl;
        isInit = true;
    }

    /**
     * 异步  json数据反序列化get请求
     *
     * @param url
     * @param params   参数集合
     * @param classOfT 反序列化目标类
     * @param listener 处理结果监听
     */
    public static void getJsonConnect(String url, HashMap<String, String> params, Class classOfT, ResultListener listener) {
        if (gson == null) throw new RuntimeException("gson未初始化");
        OkNetwork.asynchGet(mainUrl + url, params, new JsonCallback(classOfT, listener));

    }

    /**
     * 异步 get文件下载
     *
     * @param url
     * @param params
     * @param savePath 文件保存路径
     * @param listener
     */
    public static void getDownloadConnect(String url, HashMap<String, String> params, String savePath, ResultListener listener) {
        OkNetwork.asynchGet(mainUrl + url, params, new DownloadCallback(savePath, listener));
    }


    /**
     * 异步 json数据反序列化post请求
     *
     * @param url
     * @param params
     * @param classOfT
     * @param listener
     */
    public static void postJsonConnect(String url, HashMap<String, String> params, Class classOfT, ResultListener listener) {
        if (gson == null) throw new RuntimeException("gson未初始化");
        OkNetwork.asynchPost(mainUrl + url, params, new JsonCallback(classOfT, listener));
    }

    /**
     * 异步 json数据反序列化post文件上传请求
     *
     * @param url
     * @param params
     * @param files     上传文件集合
     * @param classOfT
     * @param listener  请求状态监听
     * @param pListener 上传多少字节
     */
    public static void postFileJsonConnect(String url, HashMap<String, String> params, HashMap<String, File> files, Class classOfT, ResultListener listener, ProgressListener pListener) {
        if (gson == null) throw new RuntimeException("gson未初始化");
        OkNetwork.asynchFilePost(mainUrl + url, params, files, new JsonCallback(classOfT, listener), pListener);
    }


    /**
     * 配置
     */
    public static final class InitOK {
        boolean isGosn = false;//是否使用json解析器 默认不使用
        String mainUrl;//主接口

        /**
         * 程序主接口
         */
        public InitOK mainUrl(String url) {
            mainUrl = url;
            return this;
        }

        /**
         * 是否启用json解析器
         */
        public InitOK isGosn(boolean is) {
            isGosn = is;
            return this;
        }

        /**
         * 初始化OkConnect
         */
        public void init() {
            OkConnect.inttOkConnect(this);
        }
    }
}
