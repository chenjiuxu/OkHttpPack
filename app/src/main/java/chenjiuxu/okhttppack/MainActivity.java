package chenjiuxu.okhttppack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.HashMap;

import chenjiuxu.okhttppack.http.OkConnect;
import chenjiuxu.okhttppack.http.listener.ProgressListener;
import chenjiuxu.okhttppack.http.listener.ResultListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResultListener, ProgressListener {

    private String testUrl;
    private String imgUrl;
    private String fileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testUrl = "/okhttp/Test";
        imgUrl = "/okhttp/img/5J5B5546o.JPG";
        fileUrl = "/okhttp/fileServlet";
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
        new OkConnect.InitOK().isGosn(true).mainUrl("http://192.168.1.33:8080").init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                break;
            case R.id.bt2:
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", "王五");
                OkConnect.getJsonConnect(testUrl, map, User.class, this);
                break;
            case R.id.bt3:
                OkConnect.getDownloadConnect(imgUrl, null, "/storage/emulated/0/ok图片.jpg", this);
                break;
            case R.id.bt4:
                HashMap<String, String> map1 = new HashMap<String, String>();
                map1.put("name", "陈玖旭");
                OkConnect.postJsonConnect(testUrl, map1, User.class, this);
                break;
            case R.id.bt5:
                HashMap<String, String> map2 = new HashMap<String, String>();
                map2.put("name", "陈玖旭");
                HashMap<String, File> mapFile = new HashMap<>();
                mapFile.put("file1",new File("/storage/emulated/0/ok图片.jpg"));
                mapFile.put("file2",new File("/storage/emulated/0/123456.mp4"));
                OkConnect.postFileJsonConnect(fileUrl, map2, mapFile, User.class, this, this);
                break;

        }

    }


    @Override
    public void onProgressListener(long byteCount) {
        Log.e("lll",byteCount+"");
    }

    @Override
    public void onResponse(String url, Object data) {

        Log.e("lll", data + "");
        switch (url) {
            case "/okhttp/Test":
                Log.e("kkk", data.toString());
                break;
        }
    }

    @Override
    public void onFailure(String url, Exception e) {

    }
}
