package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String userName = "root";
	private static final String password = "1234";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			while (true) {
				System.out.println("Hospital Management System");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Boook Appointment");
				System.out.println("5. Exit");

				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					// Add Patient
					patient.addPatients();
					System.out.println();
					break;
				case 2:
					// View Patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					// View Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					// Book Appointment
					bookAppointment(patient, doctor, connection, scanner);
					break;
				case 5:
					scanner.close();
					return;

				default:
					System.out.println("Enter valid choice!!!");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.print("Enter Patient Id: ");
		int patienId = scanner.nextInt();
		System.out.print("Enter Doctor Id: ");
		int doctorId = scanner.nextInt();
		System.out.print("Enter appointment date (YYYY-MM-DD): ");
		String appointmentDate = scanner.next();

		if (patient.getPatientById(patienId) && doctor.getDoctorById(doctorId)) {
			if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
				String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patienId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0) {
						System.out.println("Appointment Booked");
					} else {
						System.out.println("Failed to Book Appoinment");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Either doctor or patient doesn't exits!!!");
		}
	}

	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "select count(*) from appointments where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
