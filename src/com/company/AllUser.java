package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AllUser {

    private Gson gson = null;

    public AllUser() {
        gson = new GsonBuilder().create();
    }

    public  void allUser() throws IOException {
        URL url = new URL(Utils.getURL() + "/getAllUser");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        UserLisr user = null;
        InputStream is = http.getInputStream();
        try {
            Gson gson = new GsonBuilder().create();
            byte[] buf = responseBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            UserLisr list = gson.fromJson(strBuf, UserLisr.class);
            for (User user1 : list.getList()) {
                System.out.println(user1.getName());
            }
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
