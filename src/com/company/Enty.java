package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Enty {
    private Gson gson = null;

    public Enty() {
        gson = new GsonBuilder().create();
    }

    public void enty(String name, String pass) throws IOException {

        URL url = new URL(Utils.getURL() + "/sendUser?name=" + name + "&pass=" + pass);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        try {
            byte[] buf = responseBodyToArray(is);
            String strBuf = new String(buf, StandardCharsets.UTF_8);
            String list = gson.fromJson(strBuf, String.class);
            if (list.equalsIgnoreCase(name)) {
                System.out.println(list + " Добро пожаловать ");
            }
            else
                System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            is.close();
        }
    }
    private byte[] responseBodyToArray(InputStream is) throws IOException {
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
