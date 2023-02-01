package uap1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Main {

	Scanner scan = new Scanner(System.in);
	
	Vector<Desserts> newDessert = new Vector<>();
	Vector<Transactions> newTransaction = new Vector<>();
	// vector user
	
	Connect con = Connect.getConnection();
	
	PreparedStatement ps;
	ResultSet rs;
	
	String searchUser = "select * from Users where UserEmail = ?";
	String insertUser = "insert into Users values (?, ?, ?, ?)";
	String insertTransaction = "insert into Transactions values (?, ?, ?, ?)";
	String selectTransaction1 = "select Transactions.TransactionId, Transactions.UserId, Transactions.DessertId, Transactions.Quantity, IceCreams.DessertPrice, IceCreams.DessertName, Users.UserName from Transactions join Users on Transactions.UserId = Users.UserId join IceCreams on Transactions.DessertId = IceCreams.DessertId";
	String selectTransaction2 = "select Transactions.TransactionId, Transactions.UserId, Transactions.DessertId, Transactions.Quantity, Yoghurts.DessertPrice, Yoghurts.DessertName, Users.UserName from Transactions join Users on Transactions.UserId = Users.UserId join Yoghurts on Transactions.DessertId = Yoghurts.DessertId";
	String deleteTransaction = "delete from transactions where TransactionId = ?";
	
	public void Delete() {
		printTransactions();
		
		if(newTransaction.isEmpty() == false) {
			int choose;
			
			do {
				System.out.printf("Choose number you want to delete [1-%d]: ", newTransaction.size());
				choose = scan.nextInt();
				scan.nextLine();
			} while (choose < 1 || choose > newTransaction.size());
			
			ps = con.prepare(deleteTransaction);
			
			try {
				ps.setString(1, newTransaction.get(choose-1).getTransactionId());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Transaction " + newTransaction.get(choose-1).getTransactionId() + " Deleted");
			
			newTransaction.remove(choose-1);	
		}
		
		Prompt();
	}
	
	
	int countUser = getLastUserId();
	int countTr = getLastTrId();
	
	public void Buy() {
		String UserId = null;
		String UserName;
		String UserEmail;
		int UserAge;
		
		do {
			System.out.print("Input your name [must more than 5 characters]: ");
			UserName = scan.nextLine();
		} while (UserName.length() <= 5);
		
		do {
			System.out.print("Input your email [must contains '@' and ends with '.com']: ");
			UserEmail = scan.nextLine();
		} while (UserEmail.endsWith(".com") == false || UserEmail.contains("@") == false);
		
		do {
			System.out.print("Input your age [must greater than 0]: ");
			UserAge = scan.nextInt();
			scan.nextLine();
		} while (UserAge <= 0);
		
		ps = con.prepare(searchUser);
		
		try {
			ps.setString(1, UserEmail);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if (!rs.isBeforeFirst()) {    
			    ps = con.prepare(insertUser);
			    
			    if(countUser < 10) {
			    	UserId = String.format("US00" + countUser);
			    }else if(countUser < 100) {
			    	UserId = String.format("US0" + countUser);
			    }else {
			    	UserId = String.format("US" + countUser);
			    }
			    
				ps.setString(1, UserId);
				ps.setString(2, UserName);
				ps.setString(3, UserEmail);
				ps.setInt(4, UserAge);
				ps.executeUpdate();
				
				countUser++;
			}else {
				rs.next();
			    UserId = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newDessert.clear();
		getIceCreams();
		getYoghurts();
		printDesserts();
		
		int choose;
		int quantity;
		
		do {
			System.out.print("Choose your dessert [1-10]: ");
			choose = scan.nextInt();
			scan.nextLine();
		} while (choose < 1 || choose > 10);
		
		do {
			System.out.print("Input quantity [must greater than 0]: ");
			quantity = scan.nextInt();
			scan.nextLine();
		} while (quantity <= 0);
		
		String TransactionId;
		
		if(countTr < 10) {
			TransactionId = String.format("TR00" + countTr);
	    }else if(countTr < 100) {
	    	TransactionId = String.format("TR0" + countTr);
	    }else {
	    	TransactionId = String.format("TR" + countTr);
	    }
		
		String name = newDessert.get(choose-1).getDessertName();
		int price = newDessert.get(choose-1).getDessertPrice();
		String id = newDessert.get(choose-1).getDessertId();
		
		System.out.println("Detail Transaction");
		System.out.println("==================");
		System.out.println("Name: " + name);
		System.out.println("Price: " + price);
		System.out.println("Qty: " + quantity);
		System.out.println("Total Price: \n" + quantity * price);
		
		ps = con.prepare(insertTransaction);
		
		try {
			ps.setString(1, TransactionId);
			ps.setString(2, UserId);
			ps.setString(3, id);
			ps.setInt(4, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Successfully Inserted!");
		
		countTr++;
		
		Prompt();
	}
	
	public Integer getLastUserId() {
		String lastQuery = "SELECT UserId FROM users ORDER BY UserId DESC LIMIT 1;";
		ps = con.prepare(lastQuery);
		try {
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				String lastID = rs.getString(1);
				lastID = lastID.substring(2);
				return Integer.parseInt(lastID)+1;
			}
			else return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public Integer getLastTrId() {
		String lastQuery = "SELECT TransactionId FROM transactions ORDER BY TransactionId DESC LIMIT 1;";
		ps = con.prepare(lastQuery);
		try {
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				String lastID = rs.getString(1);
				lastID = lastID.substring(2);
				return Integer.parseInt(lastID)+1;
			}
			else return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public void printDesserts() {
		System.out.println("List dessert\n");
		System.out.println("======================================");
		System.out.println("ID | Name | Type | Price | Ingredients");
		System.out.println("======================================");
		
		int idx = 1;
		
		for (Desserts i : newDessert) {
			if(i instanceof IceCreams) {
				System.out.println(idx + " | " + i.getDessertName() + " | " + i.getDessertType() + " | " + i.getDessertPrice() + " | " + ((IceCreams) i).getCreamName());
			}else if(i instanceof Yoghurts) {
				System.out.println(idx + " | " + i.getDessertName() + " | " + i.getDessertType() + " | " + i.getDessertPrice() + " | " + ((Yoghurts) i).getProbioticName());
			}
			
			idx++;
		}
		
		System.out.println("======================================\n");
	}
	
	public void getYoghurts() {
		ps = con.prepare((new Yoghurts("", "", "", 0, "")).selectAll());
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String DessertId = rs.getString(1);
				String DessertName = rs.getString(2);
				String DessertType = rs.getString(3);
				int DessertPrice = rs.getInt(4);
				String ProbioticName = rs.getString(5);
				
				newDessert.add(new Yoghurts(DessertId, DessertName, DessertType, DessertPrice, ProbioticName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getIceCreams() {
		ps = con.prepare((new IceCreams("", "", "", 0, "")).selectAll());
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String DessertId = rs.getString(1);
				String DessertName = rs.getString(2);
				String DessertType = rs.getString(3);
				int DessertPrice = rs.getInt(4);
				String CreamName = rs.getString(5);
				
				newDessert.add(new IceCreams(DessertId, DessertName, DessertType, DessertPrice, CreamName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void View() {
		printTransactions();
		Prompt();
	}
	
	public void printTransactions() { // gak urut
		newTransaction.clear();
		
		System.out.println("All Transaction List\n");
		System.out.println("===========================================================================");
		System.out.println("No | ID | User Name | Dessert Name | Dessert Price | Quantity | Total Price");
		System.out.println("===========================================================================");
		
		int idx = 1;
		
		ps = con.prepare(selectTransaction1);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String TransactionId = rs.getString(1);
				String UserId = rs.getString(2);
				String DessertId = rs.getString(3);
				int Quantity = rs.getInt(4);
				int DessertPrice = rs.getInt(5);
				String DessertName = rs.getString(6);
				String UserName = rs.getString(7);
				
				System.out.println(idx + " | " + TransactionId + " | " + UserName + " | " + DessertName + " | " + DessertPrice + " | " + Quantity + " | " + Quantity * DessertPrice);
				
				newTransaction.add(new Transactions(TransactionId, UserId, DessertId, Quantity));
				
				idx++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ps = con.prepare(selectTransaction2);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String TransactionId = rs.getString(1);
				String UserId = rs.getString(2);
				String DessertId = rs.getString(3);
				int Quantity = rs.getInt(4);
				int DessertPrice = rs.getInt(5);
				String DessertName = rs.getString(6);
				String UserName = rs.getString(7);
				
				System.out.println(idx + " | " + TransactionId + " | " + UserName + " | " + DessertName + " | " + DessertPrice + " | " + Quantity + " | " + Quantity * DessertPrice);
				
				newTransaction.add(new Transactions(TransactionId, UserId, DessertId, Quantity));
				
				idx++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(newTransaction.isEmpty() == true) {
			System.out.println("No Transaction");
		}
		
		System.out.println("===========================================================================");
	}
	
	public void Prompt() {
		System.out.println("Press Enter to Continue...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		Menu();
	}
	
	public void Choice(int menu) {
		switch (menu) {
			case 1:{
				View();
				
				break;
			}
			case 2:{
				Buy();
				
				break;
			}
			case 3:{
				Delete();
				
				break;
			}
			case 4:{
				System.exit(0);
				
				break;
			}
		}
	}
	
	public void Menu() {
		System.out.println("Binus Dessert Check");
		System.out.println("===================");
		System.out.println("1. View All Transactions");
		System.out.println("2. Buy Dessert");
		System.out.println("3. Delete Transaction");
		System.out.println("4. Exit");
		
		int menu;
		
		do {
			System.out.print("Choose: ");
			menu = scan.nextInt();
			scan.nextLine();
			Choice(menu);
		} while (menu >= 1 || menu <= 4);
	}
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		Menu();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main();
		
	}

}
