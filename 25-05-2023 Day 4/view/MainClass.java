package com.dedalus.view;
import com.dedalus.controller.*;
import com.dedalus.exception.UserNotFoundException;
import com.dedalus.model.Employee;

import mypack.Employeee;

import java.io.*;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("Welcome to EMS :)");

		try {
			String un = null;
			String pwd = null;

			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			System.out.println("Enter Username");
			un = br.readLine();
			System.out.println("Enter Password");
			pwd = br.readLine();

			if (un.equals("Dilli") && pwd.equals("pass")) {
				System.out.println("Welcome " + un);
			} else {
				throw new UserNotFoundException();
			}

			Employeecontroller ec = new Employeecontroller();
			Scanner sc = new Scanner(System.in);
			String ch = null;

			do {
				System.out.println("Enter your choice");
				System.out.println("1. Add Employee");
				System.out.println("2. View Employee");
				System.out.println("3. Serialize to file");
				System.out.println("4. Deserialize from file");

				int choice = sc.nextInt();
				switch (choice) {
					case 1: {
						ec.addEmployee();
						break;
					}
					case 2: {
						ec.viewEmployee();
						break;
					}
					case 3: {
						try {
							Employee emp = new Employee();
							FileOutputStream fos = new FileOutputStream("dedalus.txt");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(ec);
							System.out.println("Serialized to file dedalus.txt");
							oos.close();
							fos.close();
						} catch (FileNotFoundException fnf) {
							System.out.println("No file");
						}
						break;
					}
					case 4: {
						try {
							FileInputStream fis = new FileInputStream("dedalus.txt");
							ObjectInputStream ois = new ObjectInputStream(fis);
							System.out.println("Deserialized from file dedalus.txt");
							Employeecontroller deserializedEC = (Employeecontroller) ois.readObject();
							deserializedEC.viewEmployee();
							ois.close();
							fis.close();
						} catch (FileNotFoundException fnf) {
							System.out.println("No file");
						} catch (ClassNotFoundException cnf) {
							System.out.println("No EmployeeController class");
						}
						break;
					}
					default: {
						System.out.println("Enter a valid number");
						break;
					}
				}

				System.out.println("Do you want to continue? (Y/y)");
				ch = sc.next();
			} while (ch.equalsIgnoreCase("Y"));
			System.out.println("Thanks for using!");
			System.exit(0);
		} catch (UserNotFoundException unf) {
			unf.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IO Exception");
		} finally {
			System.out.println("Finally...");
		}
	}
}
