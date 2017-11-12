package com.bj.glz.speedtestlib.ping;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class PingTester extends Thread {

    public interface PingCallback {
        void onPingCallback(PingInfo pingInfo);

        void onStart();

        void onStop();
    }

    private String mUrl = "speedtest.tele2.net";
    private int mCount = 5;
    private PingCallback mCallback;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public PingCallback getCallback() {
        return mCallback;
    }

    public void setCallback(PingCallback callback) {
        mCallback = callback;
    }

    @Override
    public void run() {
        super.run();
        executePing(mUrl, mCount, mCallback);
    }

    private void executePing(String url, int count, PingCallback callback) {
        DataOutputStream outputStream = null;
        InputStream response = null;
        try {
            Process process = Runtime.getRuntime().exec("ping -c " + count + " " + url + "\n");
            outputStream = new DataOutputStream(process.getOutputStream());
            response = process.getInputStream();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            String line;
            if (callback != null) {
                callback.onStart();
            }
            while ((line = br.readLine()) != null) {
                PingInfo info = PingInfo.parse(line);
                if (callback != null && info != null) {
                    callback.onPingCallback(info);
                }
            }
            if (callback != null) {
                callback.onStop();
            }
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class PingInfo {
        private String ip;
        private int ttl;
        private float time;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public static PingInfo parse(String line) {
            PingInfo pingInfo = null;
            if (line != null) {
                if (line.matches("\\d+\\sbytes\\sfrom.*")) {
                    pingInfo = new PingInfo();
                    try {
                        pingInfo.ip = line.replaceAll(".*?(\\d+[.]\\d+[.]\\d+[.]\\d+).*", "$1");
                    } catch (Exception e) {
                    }
                    try {
                        pingInfo.time = Float.valueOf(line.replaceAll(".*?time[=](.*?)\\sms.*", "$1"));
                    } catch (NumberFormatException e) {
                    }
                    try {
                        pingInfo.ttl = Integer.valueOf(line.replaceAll(".*?ttl[=](.*?).*", "$1"));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return pingInfo;
        }
    }
}
