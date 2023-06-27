package com.dedalus.view;

import com.dedalus.controller.*;
import com.dedalus.exception.UserNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.function.BiPredicate;

class ThreadA implements Runnable {

    public void run() {

        try {
            Thread.sleep(7000);
            System.out.println("The data is loading...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class MainClass {

    public static void main(String[] args) {
        System.out.println("Welcome to EMS :)");

        try {
            String username;
            String password;

            ThreadA df = new ThreadA();
            Thread f = new Thread(df);
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            f.start();
            System.out.println("Enter Username");
            username = br.readLine();
            System.out.println("Enter Password");
            password = br.readLine();

            BiPredicate<String, String> res = (un, pwd) -> un.equals("Dilli")&&pwd.equals("pass");

            if (res.test(username, password)) {
                System.out.println("Welcome " + username);
                Employeecontroller ec = new Employeecontroller();
                Scanner sc = new Scanner(System.in);
                String ch;

                do {
                    System.out.println("Enter your choice");
                    System.out.println("1. Add Employee");
                    System.out.println("2. View Employee");
                    System.out.println("3. Serialize to file");
                    System.out.println("4. Deserialize from file");

                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            ec.addEmployee();
                            break;
                        case 2:
                            ec.viewEmployee();
                            break;
                        case 3:
                            ec.serialize(ec.getEmplist());
                            break;
                        case 4:
                            try {
                                ec.deserialize("dedalus.txt");
                            } catch (IOException ioe) {
                                System.out.println("Error: " + ioe.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Enter a valid number");
                            break;
                    }

                    System.out.println("Do you want to continue? (Y/y)");
                    ch = sc.next();
                } while (ch.equalsIgnoreCase("Y"));

                System.out.println("Thanks for using!");
                System.exit(0);

            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException unf) {
            unf.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        } finally {
            System.out.println("Finally...");
        }
    }
}
