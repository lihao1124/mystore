package com.bj.glz.speedtestlib.uploader;

import android.os.Process;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/5/17
 */

public class Uploader extends Thread {


    private UploadConfig mUploadConfig;

    private boolean mIsStop = false;

    public UploadConfig getUploadConfig() {
        return mUploadConfig;
    }

    public void setUploadConfig(UploadConfig uploadConfig) {
        mUploadConfig = uploadConfig;
    }

    @Override
    public void run() {
        super.run();
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (mUploadConfig == null) {
                mUploadConfig = UploadConfig.getDefaultConfig();
            }
            upload();
        } catch (Exception e) {
        }
    }

    public void safeStop() {
        mIsStop = true;
    }

    private void upload() throws Exception {
        long startTime = System.currentTimeMillis();
        URLConnection connection = new URL("http://dallas3.testmy.net/uploader").openConnection();
        connection.addRequestProperty("Content-Type",
                "multipart/form-data; boundary=----WebKitFormBoundary9EPM93v81QxEztMd");
        connection.addRequestProperty("Origin", "http://dallas3.testmy.net");
        connection.addRequestProperty("Referer", "http://dallas3.testmy.net/ul-2560");
        connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
        connection.addRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(1000);
        connection.connect();
        OutputStream out = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writeInfo("Content-Disposition: form-data; name=\"test_html\"", "test_html", writer);
        writeInfo("Content-Disposition: form-data; name=\"title\"", null, writer);
        writeInfo("Content-Disposition: form-data; name=\"size_print\"", "2621440 bytes", writer);
        writeInfo("Content-Disposition: form-data; name=\"size_data\"", "2621440", writer);
        writeInfo("Content-Disposition: form-data; name=\"ta\"", null, writer);
        writeInfo("Content-Disposition: form-data; name=\"top\"", null, writer);
        writeInfo("Content-Disposition: form-data; name=\"test_type\"", "upload", writer);
        byte[] b = new byte[1024];
        for (int i = 0; i < 1024; i++) {
            b[i] = 'a';
        }
        for (int i = 0; i < 512 * 5; i++) {
            if (System.currentTimeMillis() - startTime > mUploadConfig.getTestDuration() || mIsStop) {
                endTransfer(writer);
                closeStream(out);
                closeStream(writer);
                closeStream(connection.getInputStream());
                return;
            }
            out.write(b);
        }
        closeStream(out);
        closeStream(writer);
        closeStream(connection.getInputStream());
    }

    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    private void endTransfer(PrintWriter writer) {
        writer.println("------WebKitFormBoundary9EPM93v81QxEztMd--");
        writer.println();
        writer.println();
        writer.flush();
    }

    private static void writeInfo(String info, String data, PrintWriter writer) {
        writer.println("------WebKitFormBoundary9EPM93v81QxEztMd");
        writer.println(info);
        writer.println();
        if (data == null) {
            writer.println();
        } else {
            writer.println(data);
        }
    }
}
