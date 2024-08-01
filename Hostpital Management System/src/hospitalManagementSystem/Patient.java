package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatients() {
		System.out.print("Enter Patient Name: ");
		String name = scanner.next();
		System.out.print("Enter Patient Age: ");
		int age = scanner.nextInt();
		System.out.print("Enter Patient Gender: ");
		String gender = scanner.next();

		try {
			String query = "insert into patients(name,age,gender) values(?,?,?)";
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, name);
			prepareStatement.setInt(2, age);
			prepareStatement.setString(3, gender);
			int affectedRows = prepareStatement.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Patient Added successfully!!!");

			} else {
				System.out.println("Failed to add Patient!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Patient: ");
			System.out.println("+------------+--------------+----------+---------------+");
			System.out.println("| Patient Id |Name          |Age	   |Gender         |");
			System.out.println("+------------+--------------+----------+---------------+");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
				System.out.println("+------------+--------------+----------+---------------+");
				

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getPatientById(int id) {
		String query = "select * from patients where id =?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resueltSet = preparedStatement.executeQuery();
			if (resueltSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
