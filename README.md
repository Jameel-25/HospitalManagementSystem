Hospital Management System

This project is a simple Hospital Management System written in Java. It provides basic functionalities to manage patients, doctors, and appointments within a hospital. The system uses a MySQL database to store the data and JDBC for database connectivity.

Features

Add Patients: Allows the user to add new patients to the system by providing their name, age, and gender.
View Patients: Displays a list of all patients currently stored in the database.
View Doctors: Displays a list of all doctors along with their specializations.
Book Appointments: Enables booking appointments for patients with doctors on specific dates. Ensures that a doctor is available on the requested date before booking.
Database Structure

The project uses a MySQL database with the following tables:


patients: Stores patient information (id, name, age, gender).
doctors: Stores doctor information (id, name, specialization).
appointments: Stores appointment information (id, patient_id, doctor_id, appointment_date).
Getting Started

Prerequisites

Java Development Kit (JDK)
MySQL Server
JDBC Driver for MySQL
Setup
Clone the repository:

sh
Copy code
git clone 
https://github.com/Jameel-25/hospital-management-system.git
Configure the database:


Create a new MySQL database named hospital.


Create the necessary tables using the following SQL scripts:

sql
Copy code

CREATE TABLE patients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(10) NOT NULL
);


CREATE TABLE doctors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  specialization VARCHAR(50) NOT NULL
);


CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  patient_id INT,
  doctor_id INT,
  appointment_date DATE,
  FOREIGN KEY (patient_id) REFERENCES patients(id),
  FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

Update database configuration:

Update the url, userName, and password variables in the HospitalManagementSystem class with your MySQL database credentials.
Compile and run the project:

Open the project in your preferred IDE.
Compile and run the HospitalManagementSystem class.

Usage

The application will prompt you with a menu where you can choose to add patients, view patients, view doctors, or book appointments.
Follow the on-screen instructions to perform the desired operations.

License

This project is licensed under the MIT License. See the LICENSE file for more details.
