package com.company;

import java.io.IOException;
import java.util.Scanner;

public class SetStatus {
    public static void setStat() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        String log = scanner.nextLine();
        System.out.println("Enter your Statys: ");
        boolean status = scanner.hasNextBoolean();
        User user = new User(log, status);
        int resLog = user.send(Utils.getURL() + "/setStatus");
        if (resLog != 200) { // 200 OK
            System.out.println("HTTP error occured: " + resLog);
        }
    }
}
