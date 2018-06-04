import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HousingMain {
	final static String driverClass = "org.sqlite.JDBC";
	final static String url = "jdbc:sqlite:housingDB.sqlite";


	public static void main(String[] argv){		
		runHousingMainPage();
	}
	
	public static void runHousingMainPage() {
		
		//Search Residents by lname, fname, umassID, roomNum
		//Check-in (only allow to check-in individual who have their room reserved, update individual to "in room" status)
		//Check-out (record individual into History table)
		//Check current building status (check number of empty apartment & occupied apartment)
		
		printPartitionLine();
		System.out.println("\nHousing Application");
		
		while(true) {
			System.out.println("Main Page:\n1. Search Resident\n2. Check-In\n3. Check-out\n4. View Building's Information");
			System.out.print("Enter the number of the function you would like to use: ");
		
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			switch(userChoice) {
				case "1": runSearchPage();
					break;
				case "2": runCheckInPage();
					break;
				case "3": runCheckOutPage();
					break; 
				case "4": runBuildingInfoPage();
					break;
				default: printPartitionLine(); System.out.print("INVALID ENTRY! Please try again!\n");
					break;
			}//end of switch
		}//end of while
				
	}
	
	public static void runSearchPage() {
		printPartitionLine();
		System.out.println("\nHousing Application");
		
		while(true) {
			System.out.println("Search Page:\n1. by Last Name?\n2. by First Name?\n3. by UMass ID?\n4. by Room Number?\n5. Back to Main Page");
			System.out.print("Enter the number of the function you would like to use: ");
		
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			switch(userChoice) {
				case "1": searchByLastName();
					break;
				case "2": searchByFirstName();
					break;
				case "3": searchByID();
					break;
				case "4": searchByRoom();
					break;
				case "5": runHousingMainPage();
					break;
				default: printPartitionLine(); System.out.print("INVALID ENTRY! Please try again!\n");
					break;
			}//end of switch
		}//end of while
	}
	
	public static void searchByLastName() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Search By Last Name...");
			System.out.print("Enter BACK or last name: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			
			if(userChoice.equals("BACK")) {
				runSearchPage();
			}else {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				String in = userChoice;

				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);

					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search Last name (residents)
					query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE lname like '%" + in + "%'";
					ResultSet results = stmnt.executeQuery(query);

					printPartitionLine();
					System.out.println("\nResults:");
					System.out.print("First Name - Last Name - UMass ID - UMass Email - Status - Room Number");
					printPartitionLine();
					int count = 0;
					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
						String roomNum = results.getString(5);
						String building = results.getString(6);
						String status = results.getString(7);
						
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + status + " - " + building + "-" + roomNum);
					}
					
					//Search Last name (non-residents)
					query = "SELECT fname, lname, umassID, umassEmail FROM people where lname like '%" + in + "%'";
					query = query + "EXCEPT SELECT fname, lname, people.umassID, umassEmail FROM people natural join occupy WHERE lname like '%" + in + "%'";
					results = stmnt.executeQuery(query);

					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
					
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - Non-residents");
					}
					
					if(count == 0) {
						System.out.println("Unfortunately no such individual's last name: " + userChoice + " exist in the system...");
					}
					count = 0;
					printPartitionLine();
				

				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}
			}//end of while
		}//end of if
	}
	
	public static void searchByFirstName() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Search By First Name...");
			System.out.print("Enter BACK or first name: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			
			if(userChoice.equals("BACK")) {
				runSearchPage();
			}else {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				String in = userChoice;

				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);

					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search First name (residents)
					query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE fname like '%" + in + "%'";
					ResultSet results = stmnt.executeQuery(query);

					printPartitionLine();
					System.out.println("\nResults:");
					System.out.print("First Name - Last Name - UMass ID - UMass Email - Status - Room Number");
					printPartitionLine();
					int count = 0;
					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
						String roomNum = results.getString(5);
						String building = results.getString(6);
						String status = results.getString(7);
						
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + status + " - " + building + "-" + roomNum);
					}
					
					//Search First name (non-residents)
					query = "SELECT fname, lname, umassID, umassEmail FROM people where fname like '%" + in + "%'";
					query = query + "EXCEPT SELECT fname, lname, people.umassID, umassEmail FROM people natural join occupy WHERE fname like '%" + in + "%'";
					results = stmnt.executeQuery(query);

					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
					
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - Non-residents");
					}
					
					if(count == 0) {
						System.out.println("Unfortunately no such individual's first name: " + userChoice + " exist in the system...");
					}
					count = 0;
					printPartitionLine();
				

				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}
			}//end of while
		}//end of if
	}
	
	public static void searchByID() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Search By UMass ID...");
			System.out.print("Enter BACK or the 8 Digits UMass ID (including the 0 at the begining): ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			char test = userChoice.charAt(0);
			if(test == '0') {
				userChoice = userChoice.substring(1);
			}
			
			if(userChoice.equals("BACK")) {
				runSearchPage();
			}else {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				String in = userChoice;
				if(isInteger(in)) {

				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);

					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search ID (residents)
					query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE umassID = " + in;
					ResultSet results = stmnt.executeQuery(query);

					printPartitionLine();
					System.out.println("\nResults:");
					System.out.print("First Name - Last Name - UMass ID - UMass Email - Status - Room Number");
					printPartitionLine();
					int count = 0;
					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
						String roomNum = results.getString(5);
						String building = results.getString(6);
						String status = results.getString(7);
						
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + status + " - "+ building + "-" + roomNum);
					}
					
					//Search ID (non-residents)
					query = "SELECT fname, lname, umassID, umassEmail FROM people WHERE umassID = " + in + " ";
					query = query + "EXCEPT SELECT fname, lname, people.umassID, umassEmail FROM people natural join occupy WHERE umassID = " + in;
					results = stmnt.executeQuery(query);

					while (results.next() && count < 1) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
					
						System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - Non-residents");
					}
					
					
					if(count == 0) {
						System.out.println("Unfortunately no such individual's UMass ID: 0" + userChoice + " exist in the system...");
					}
					count = 0;
					printPartitionLine();
				

				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}
			}//end of if
			}
		}//end of while
	}
	
	public static void searchByRoom() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Search By Room Number (Example: 5126-B or 5126)...");
			System.out.print("Enter BACK or the Room Number: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
		
			if(userChoice.equals("BACK")) {
				runSearchPage();
			}else {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				String in = userChoice;

				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);

					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search Room Num (residents)
					query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE roomNum like '%" + in + "%'";
					ResultSet results = stmnt.executeQuery(query);

					printPartitionLine();
					System.out.println("\nResults:");
					System.out.print("Room Number - Status - First Name - Last Name - UMass ID - UMass Email");
					printPartitionLine();
					int count = 0;
					while (results.next()) {
						count ++;
						String fname = results.getString(1);
						String lname = results.getString(2);
						String umassID = results.getString(3);
						String umassEmail = results.getString(4);
						String roomNum = results.getString(5);
						String building = results.getString(6);
						String status = results.getString(7);
						
						System.out.println(building + "-" + roomNum + " - " + status + " - " + fname + " - " + lname + " - 0" + umassID + " - " + umassEmail);
					}
					
					//Search Room Number (non-residents)
					query = "SELECT roomNum, building FROM rooms WHERE roomNum like '%" + in + "%' ";
					query = query + "EXCEPT SELECT roomNum, building FROM people natural join occupy natural join rooms WHERE roomNum like '%" + in + "%'";
					results = stmnt.executeQuery(query);

					while (results.next()) {
						count ++;
						String roomNum = results.getString(1);
						String building = results.getString(2);
					
						System.out.println(building + "-" + roomNum + " - Currently not reserved or occupied by anyone");
					}
					
					
					if(count == 0) {
						System.out.println("Unfortunately no such Room Number: " + userChoice + " exist in the system...");
					}
					count = 0;
					printPartitionLine();
				

				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}
			}//end of if
		}//end of while
	}
	
	public static void runCheckInPage() {
		printPartitionLine();
		System.out.println("\nHousing Application");
		
		while(true) {
			System.out.println("Check-In Residents Page:\n1. View all current reserved room?\n2. Check-in a resident?\n3. Back to Main Page");
			System.out.print("Enter the number of the function you would like to use: ");
		
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			switch(userChoice) {
				case "1": viewAllReserved();
					break;
				case "2": checkInResidents();
					break;
				case "3": runHousingMainPage();
					break;
				default: printPartitionLine(); System.out.print("INVALID ENTRY! Please try again!\n");
					break;
			}//end of switch
		}//end of while
	}
	
	public static void viewAllReserved() {
		printPartitionLine();
		System.out.println("\nHousing Application");
			
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		Connection connection = null;
		String query = null;

		try {
			// Load the JDBC drivers
			Class.forName(driverClass);
			
			// Open a DB Connection
			connection = DriverManager.getConnection(url);

			// Create a Statement
			Statement stmnt = connection.createStatement();

			//Search Room Num (residents)
			query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE status = 'Reserved'";
			ResultSet results = stmnt.executeQuery(query);

			printPartitionLine();
			System.out.println("\nResults:");
			System.out.print("First Name - Last Name - UMass ID - UMass Email - Status - Room Number");
			printPartitionLine();
			while (results.next()) {
				String fname = results.getString(1);
				String lname = results.getString(2);
				String umassID = results.getString(3);
				String umassEmail = results.getString(4);
				String roomNum = results.getString(5);
				String building = results.getString(6);
				String status = results.getString(7);
						
				System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + status + " - " + building + "-" + roomNum);
			}
				
			//printPartitionLine();
			checkInResidents();
				
		} catch (Exception e) {
				System.out.println(query);
				e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void checkInResidents() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Check-In a Resident...");
			System.out.print("Enter BACK or type in the resident's first name followed by last name: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			boolean check = false;
			
			if(userChoice.equals("BACK")) {
				runCheckInPage();
			}else {
				
				String[] splited = null;
				if(userChoice.contains(" ")) {
					splited = userChoice.split("\\s+");
					check = true;
				}else {
					check = false;
				}
				
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);
					
					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search Room Num (residents)
					int count = 0;
					String roomNum = "", building = "";
					if(check) {
						query = "SELECT rooms.roomNum, building FROM people natural join occupy natural join rooms WHERE fname = '" + splited[0] + "' and lname = '" + splited[1] + "' and status = 'Reserved'";
						ResultSet results = stmnt.executeQuery(query);
					
						while (results.next()) {
							count++;
							roomNum = results.getString(1);
							building = results.getString(2);
						}
						
						if(count>0) {
							LocalDateTime now = LocalDateTime.now();
							int year = now.getYear();
							int month = now.getMonthValue();
							int day = now.getDayOfMonth();
							String date;
					
							if(day<10) {
								date = month + "/0" + day + "/" + year;
							}else {
								date = month + "/" + day + "/" + year;
							}
					
							//Search Room Num (residents)
							query = "UPDATE occupy SET status = 'In Room' , checkInDate = '" + date + "' "
									+ "WHERE status = 'Reserved' and occupy.umassID "
									+ "IN (SELECT people.umassID FROM people, occupy WHERE people.umassID = occupy.umassID and "
									+ "people.fname = '" + splited[0] + "' " + "and people.lname = '" + splited[1] + "')";
					
							PreparedStatement pstmt = connection.prepareStatement(query);
							pstmt.executeUpdate();
							printPartitionLine();
							System.out.println("\nChecking-In Resident...\nSuccesfully checked-in residents " + splited[0] + " " + splited[1] + " into room " + building + "-" + roomNum);
							printPartitionLine();
						}else {
							printPartitionLine();
							System.out.println("\nUnfortunately no such individual's name: "+ userChoice + " exist in the system or in the Reserved list...");
							printPartitionLine();
						}
					}else {
						printPartitionLine();
						System.out.println("\nUnfortunately no such individual's name: "+ userChoice + " exist in the system or in the Reserved list...");
						printPartitionLine();
					}
					
					count = 0;
					check = false;
					
				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}//end of try catch finally
			}//end of if else
		}//end of while
	}
	
	public static void runCheckOutPage() {
		printPartitionLine();
		System.out.println("\nHousing Application");
		
		while(true) {
			System.out.println("Check-Out Page:\n1. View all current In Room residents?\n2. by First & Last Name?\n3.  Back to Main Page");
			System.out.print("Enter the number of the function you would like to use: ");
		
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			switch(userChoice) {
				case "1": viewAllInRoom();
					break;
				case "2": checkOutResidents();
					break;
				case "3": runHousingMainPage();
					break;
				default: printPartitionLine(); System.out.print("INVALID ENTRY! Please try again!\n");
					break;
			}//end of switch
		}//end of while
	}
	
	public static void viewAllInRoom() {
		printPartitionLine();
		System.out.println("\nHousing Application");
			
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		Connection connection = null;
		String query = null;

		try {
			// Load the JDBC drivers
			Class.forName(driverClass);

			// Open a DB Connection
			connection = DriverManager.getConnection(url);

			// Create a Statement
			Statement stmnt = connection.createStatement();

			//Search Room Num (residents)
			query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE status = 'In Room'";
			ResultSet results = stmnt.executeQuery(query);

			printPartitionLine();
			System.out.println("\nResults:");
			System.out.print("First Name - Last Name - UMass ID - UMass Email - Status - Room Number");
			printPartitionLine();
			while (results.next()) {
				String fname = results.getString(1);
				String lname = results.getString(2);
				String umassID = results.getString(3);
				String umassEmail = results.getString(4);
				String roomNum = results.getString(5);
				String building = results.getString(6);
				String status = results.getString(7);
						
				System.out.println(fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + status + " - " + building + "-" + roomNum);
			}
				
			//printPartitionLine();
			checkOutResidents();
				
			} catch (Exception e) {
				System.out.println(query);
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (Exception e) {
			}
		}
	}
	
	public static void checkOutResidents() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("Check-Out a Resident...");
			System.out.print("Enter BACK or type in the resident's first name followed by last name: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			boolean check = false;
			
			if(userChoice.equals("BACK")) {
				runCheckOutPage();
			}else {
				
				String[] splited = null;
				if(userChoice.contains(" ")) {
					splited = userChoice.split("\\s+");
					check = true;
				}else {
					check = false;
				}
				
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);
					
					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search Room Num (residents)
					int count = 0;
					String umassID = "", checkInDate = "", roomNum = "", building = "";
					if(check) {
						query = "SELECT occupy.umassID, occupy.checkInDate, occupy.roomNum, building FROM people natural join occupy natural join rooms WHERE fname = '" + splited[0] + "' and lname = '" + splited[1] + "' and status = 'In Room'";
						ResultSet results = stmnt.executeQuery(query);
					
						while (results.next()) {
							count++;
							umassID = results.getString(1);
							checkInDate = results.getString(2);
							roomNum = results.getString(3);
							building = results.getString(4);
						}
						
						if(count>0) {
							LocalDateTime now = LocalDateTime.now();
							int year = now.getYear();
							int month = now.getMonthValue();
							int day = now.getDayOfMonth();
							String checkOutDate;
					
							if(day<10) {
								checkOutDate = month + "/0" + day + "/" + year;
							}else {
								checkOutDate = month + "/" + day + "/" + year;
							}
					
							//Search Room Num (residents)
							query = "INSERT INTO occupyHistory(umassID, roomNum, checkInDate, checkOutDate) VALUES('" + umassID + "', '" + roomNum + "', '"  + checkInDate + "', '"  + checkOutDate +  "')";
							PreparedStatement pstmt = connection.prepareStatement(query);
							pstmt.executeUpdate();
							
							query = "DELETE from occupy WHERE umassID = " + umassID;
							pstmt = connection.prepareStatement(query);
							pstmt.executeUpdate();
							
							printPartitionLine();
							System.out.println("\nChecking-Out Resident...\nSuccesfully checked-out residents " + splited[0] + " " + splited[1] + " from room " + building + "-" + roomNum);
							printPartitionLine();
						}else {
							printPartitionLine();
							System.out.println("\nUnfortunately no such individual's name: "+ userChoice + " exist in the system or in the In Room list...");
							printPartitionLine();
						}
					}else {
						printPartitionLine();
						System.out.println("\nUnfortunately no such individual's name: "+ userChoice + " exist in the system or in the In Room list...");
						printPartitionLine();
					}
					
					count = 0;
					check = false;
					
				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}//end of try catch finally
			}//end of if else
		}//end of while
	}
	
	public static void runBuildingInfoPage() {
		printPartitionLine();
		System.out.println("\nHousing Application");
		
		while(true) {
			System.out.println("Building's Information Page:\n1. View all occupied & reserved rooms?\n2. View all unoccupied rooms?\n3. View mailbox information?\n4. View Occupancy History?\n5. Back to Main Page");
			System.out.print("Enter the number of the function you would like to use: ");
		
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
			switch(userChoice) {
				case "1": viewAllInRoomReserved();
					break;
				case "2": viewAllNotOccupied();
					break;
				case "3": viewMailBoxInfo();
					break;
				case "4": viewHistory();
					break;
				case "5": runHousingMainPage();
					break;
				default: printPartitionLine(); System.out.print("INVALID ENTRY! Please try again!\n");
					break;
			}//end of switch
		}//end of while
	}
	
	public static void viewAllInRoomReserved(){
		printPartitionLine();
		System.out.println("\nHousing Application");
			
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		Connection connection = null;
		String query = null;

		try {
			// Load the JDBC drivers
			Class.forName(driverClass);

			// Open a DB Connection
			connection = DriverManager.getConnection(url);

			// Create a Statement
			Statement stmnt = connection.createStatement();

			//Search Room Num (residents)
			query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE status = 'In Room'";
			ResultSet results = stmnt.executeQuery(query);

			int count = 0;
			printPartitionLine();
			System.out.println("\nResults:");
			System.out.print("Room Number - Status - First Name - Last Name - UMass ID - UMass Email");
			printPartitionLine();
			while (results.next()) {
				count++;
				String fname = results.getString(1);
				String lname = results.getString(2);
				String umassID = results.getString(3);
				String umassEmail = results.getString(4);
				String roomNum = results.getString(5);
				String building = results.getString(6);
				String status = results.getString(7);
						
				System.out.println(count + ".) "+ building + "-" + roomNum + " - " + status + " - "  + fname + " - " + lname + " - 0" + umassID + " - " + umassEmail);
			}
				
				
			query = "SELECT fname, lname, people.umassID, umassEmail, rooms.roomNum, building, status FROM people natural join occupy natural join rooms WHERE status = 'Reserved'";
			results = stmnt.executeQuery(query);
			while (results.next()) {
				count ++;
				String fname = results.getString(1);
				String lname = results.getString(2);
				String umassID = results.getString(3);
				String umassEmail = results.getString(4);
				String roomNum = results.getString(5);
				String building = results.getString(6);
				String status = results.getString(7);
						
				System.out.println(count + ".) "+ building + "-" + roomNum + " - " + status + " - "  + fname + " - " + lname + " - 0" + umassID + " - " + umassEmail);
			}
			//printPartitionLine();
			runBuildingInfoPage();
				
			} catch (Exception e) {
				System.out.println(query);
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (Exception e) {
			}
		}
	}
	
	public static void viewAllNotOccupied(){
		printPartitionLine();
		System.out.println("\nHousing Application");
			
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		Connection connection = null;
		String query = null;

		try {
			// Load the JDBC drivers
			Class.forName(driverClass);

			// Open a DB Connection
			connection = DriverManager.getConnection(url);

			// Create a Statement
			Statement stmnt = connection.createStatement();

			//Search Room Num (residents)
			query = "SELECT roomNum, building FROM rooms "
					+ "EXCEPT SELECT rooms.roomNum, building FROM people natural join occupy natural join rooms";
			ResultSet results = stmnt.executeQuery(query);

			int count = 0;
			printPartitionLine();
			System.out.println("\nResults:");
			System.out.print("Room that's currently not occupied or reserved");
			printPartitionLine();
			while (results.next()) {
				count++;
				String roomNum = results.getString(1);
				String building = results.getString(2);
						
				System.out.println(count + ".) "+ building + "-" + roomNum);
			}
				
			//printPartitionLine();
			runBuildingInfoPage();
				
		} catch (Exception e) {
			System.out.println(query);
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				}
		}
	}
	
	public static void viewMailBoxInfo() {
		printPartitionLine();
		while(true) {
			System.out.println("\nHousing Application");
			System.out.println("View Mailbox Information - Search By Room Number (Example: 5126-B or 5126)...");
			System.out.print("Enter BACK or the Room Number: ");
			
			Scanner scanner = new Scanner(System.in);
			String userChoice = scanner.nextLine();
		
			if(userChoice.equals("BACK")) {
				runBuildingInfoPage();
			}else {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				Connection connection = null;
				String query = null;
				
				String in = userChoice;

				try {
					// Load the JDBC drivers
					Class.forName(driverClass);

					// Open a DB Connection
					connection = DriverManager.getConnection(url);

					// Create a Statement
					Statement stmnt = connection.createStatement();

					//Search Room Num (residents)
					query = "SELECT rooms.roomNum, building, mailboxNum, mailboxCombo FROM rooms natural join mailbox WHERE roomNum like '%" + in + "%'";
					ResultSet results = stmnt.executeQuery(query);

					printPartitionLine();
					System.out.println("\nResults:");
					System.out.print("Room Number - Mailbox Number - Mailbox Combination");
					printPartitionLine();
					int count = 0;
					while (results.next()) {
						count ++;
						String roomNum = results.getString(1);
						String building = results.getString(2);
						String mailboxNum = results.getString(3);
						String mailboxCombo = results.getString(4);
						
						System.out.println(building + "-" + roomNum + " - " + mailboxNum + " - " + mailboxCombo);
					}
					
					if(count == 0) {
						System.out.println("Unfortunately no such Room Number: " + userChoice + " exist in the system...");
					}
					count = 0;
					printPartitionLine();
				

				} catch (Exception e) {
					System.out.println(query);
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (Exception e) {
					}
				}
			}//end of if
		}//end of while
	}
	
	public static void viewHistory() {
		printPartitionLine();
		System.out.println("\nHousing Application");
			
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		Connection connection = null;
		String query = null;

		try {
			// Load the JDBC drivers
			Class.forName(driverClass);

			// Open a DB Connection
			connection = DriverManager.getConnection(url);

			// Create a Statement
			Statement stmnt = connection.createStatement();

			//Search Room Num (residents)
			query = "SELECT fname, lname, people.umassID, people.umassEmail, rooms.roomNum, building, checkInDate, checkOutDate FROM people natural join occupyHistory natural join rooms";
			ResultSet results = stmnt.executeQuery(query);

			int count = 0;
			printPartitionLine();
			System.out.println("\nResults:");
			System.out.print("First Name - Last Name - UMass ID - UMass Email - Room Number - Check In Date - Check Out Date");
			printPartitionLine();
			while (results.next()) {
				count++;
				String fname = results.getString(1);
				String lname = results.getString(2);
				String umassID = results.getString(3);
				String umassEmail = results.getString(4);
				String roomNum = results.getString(5);
				String building = results.getString(6);
				String checkInDate = results.getString(7);
				String checkOutDate = results.getString(8);
						
				System.out.println(count + ".) "+ fname + " - " + lname + " - 0" + umassID + " - " + umassEmail + " - " + building + "-" + roomNum + " - " + checkInDate + " - " + checkOutDate);
			}
				
			//printPartitionLine();
			runBuildingInfoPage();
				
		} catch (Exception e) {
			System.out.println(query);
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				}
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	    	printPartitionLine();
	    	System.out.print("\nInput is not 8 digit UMass ID...");
	        return false; 
	    } catch(NullPointerException e) {
	    	printPartitionLine();
	    	System.out.print("\nInput is not 8 digit UMass ID...");
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static void printPartitionLine() {
		System.out.println("\n------------------------------------------------------------------------------------");
	}
}