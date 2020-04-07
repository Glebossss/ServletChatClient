package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CheckStatys {
    private Gson gson = null;

    public CheckStatys() {
        gson = new GsonBuilder().create();
    }
    public void checkStatys(String log, String pass) throws IOException {
        URL url = new URL(Utils.getURL() + "/setStatus?login=" + log + "&pass=" + pass);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        UserLisr user = null;
        InputStream is = http.getInputStream();
        try {
            Gson gson = new GsonBuilder().create();
            byte[] buf = responseBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            User list = gson.fromJson(strBuf, User.class);
            System.out.println(list.isStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
