package hospitalmanag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalmanagementSystem {

	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final  String password="root@123";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner= new Scanner(System.in);
		try {
			Connection connection= DriverManager.getConnection(url,username,password);
			Patient pat= new Patient(connection,scanner);
		Doctor doctor= new Doctor(connection );
		while(true) {

			System.out.println("hospital managment system");
			System.out.println("1. Add patient");
			System.out.println("2. View patient");
			System.out.println("3. view Doctor");
			System.out.println("4. Book Appointment");
			System.out.println("5. Exit");
			System.out.println("Enter your choice");
			int choice= scanner.nextInt();


			switch(choice) {
			case 1 :

				//add
				pat.addPatient();
			case 2 :
				//add
				pat.ViewPatients();
			case 3 :
				//add
				doctor.viewDoctor();
			case 4 :
				//add

			case 5 :
				//add
				return;
				default:
					System.out.print("Invalid choice");

			}

		}



		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void bookAppoinment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
	System.out.println("Enter patient id");
	int patientid= scanner.nextInt();
	System.out.println("Enter Doctor id");
	int doctorid= scanner.nextInt();
	System.out.println("Enter appoinment date (YYYY-MM-DD) ");
	String appointmentDate = scanner.next();
	if(patient.getPatientByID(patientid)&&doctor.getDoctorByID(doctorid)){

		if(Checkdoctoravilability(doctorid,appointmentDate,connection)) {
			String appoinmentQuery= "INSERT INTO appointments(patient_id,appointment_date)VALUES(?,?,?)";

			try {
				 PreparedStatement preparedStatement = connection.prepareStatement(appoinmentQuery);
				 preparedStatement.setInt(1, patientid);
				 preparedStatement.setInt(2, doctorid);
				 preparedStatement.setString(3, appointmentDate);
				 int rowsaff = preparedStatement.executeUpdate();

				 if(rowsaff>0) {
					 System.out.println("Appointment Booked!");

				 }else {
					 System.out.print("Failed to book appointment");

				 }

			}catch(SQLException e) {
				e.printStackTrace();
			}

		}else {
			System.out.println("doctor not avilable on this date");
		}

	}else {
		System.out.print("either Doctor or patient does doesn't exist!!!");
	}




	}

	public  static boolean Checkdoctoravilability(int doctorid, String appointmentDate,Connection connection) {
		// TODO Auto-generated method stub
		 String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
		 try{
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, doctorid);
	            preparedStatement.setString(2, appointmentDate);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	                int count = resultSet.getInt(1);
	                if(count==0){
	                    return true;
	                }else{
	                    return false;
	                }
	            }
	        } catch (SQLException e){
	            e.printStackTrace();
	        }
		 return false;

}
}