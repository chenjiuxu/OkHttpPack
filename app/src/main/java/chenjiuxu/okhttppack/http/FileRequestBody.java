package chenjiuxu.okhttppack.http;

import java.io.File;
import java.io.IOException;

import chenjiuxu.okhttppack.http.listener.ProgressListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by 15705 on 2017/9/20.
 * 文件请求体
 */
public class FileRequestBody extends RequestBody {
    private ProgressListener listener;//监听
    private MediaType contentType;
    private File file;

    public FileRequestBody(MediaType contentType, File file, ProgressListener pListener) {
        this.contentType = contentType;
        this.file = file;
        this.listener = pListener;
    }

    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        try (Source source = Okio.source(file)) {
            Buffer buf = new Buffer();
            long readCount = 0;//一次读取字节
            while ((readCount = source.read(buf, 10240)) != -1) {



                sink.write(buf, readCount);
                if (listener != null) listener.onProgressListener(readCount);
            }
            sink.flush();
        }
    }
}
