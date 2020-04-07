package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.nio.cs.US_ASCII;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        AllUser allUserr = new AllUser();
        Gson gson = new Gson();
        Enty enty = new Enty();
        CheckStatys checkStatys = new CheckStatys();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Enter your pass: ");
        String pass = scanner.nextLine();
        User user = new User(login, pass);
        int resLog = user.send(Utils.getURL() + "/log");
        if (resLog != 200) { // 200 OK
            System.out.println("HTTP error occured: " + resLog);
            return;
        }
        enty.enty(login, pass);
        while (true) {
            System.out.println("1 - отправить смс 2 - приватное сообщение 3 - Список всех пользователей" +
                    "4 проверка статуса 5 - выход");
            int stap = scanner.nextInt();
            switch (stap) {
                case 1:
                    sandMassaeg(login);
                    break;
                case 2:
                    sandPrivatMassaeg(login);
                    break;
                case 3:
                    allUserr.allUser();
                break;
                case 4:
                    SetStatus.setStat();
                    break;
                case 5:
                    checkStatys.checkStatys(login,pass);
                    break;
            }
        }
    }


    private static void sandPrivatMassaeg(String login) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Кому ? ");
        String toMassage = scanner.nextLine();
        Thread th = new Thread(new PrivatMassage());
        th.setDaemon(true);
        th.start();
        System.out.println("Enter your message: ");
        while (true) {
            String text = scanner.nextLine();
            if (text.isEmpty()) break;
            Message m = new Message(login, text, toMassage);
            int res = m.send(Utils.getURL() + "/add");
            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }

        }
    }


    private static void sandMassaeg(String login) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Thread th = new Thread(new GetThread());
        th.setDaemon(true);
        th.start();
        System.out.println("Enter your message: ");
        while (true) {
            String text = scanner.nextLine();
            if (text.isEmpty()) break;
            Message m = new Message(login, text);
            int res = m.send(Utils.getURL() + "/add");
            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }
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
