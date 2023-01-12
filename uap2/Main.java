package uap2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
	
	Connect con = Connect.getConnection();
	
	PreparedStatement ps;
	ResultSet rs;
	
	Scanner scan = new Scanner(System.in);
	
	Vector<Drink> newDrink = new Vector<>();
	Vector<Transaction> newTransaction = new Vector<>();
 	
	public void Delete() {
		getTransaction();
		
		if(newTransaction.isEmpty() == true) {
			System.out.println("There is no transaction...");
		}else {
			printTransaction();
			
			int choose;
			
			do {
				System.out.printf("Input the number of index you want to delete[1 .. %d] : ", newTransaction.size());
				choose = scan.nextInt();
				scan.nextLine();
			} while (choose < 1 || choose > newTransaction.size());
			
			String query = "delete from Transaction where TransactionID = ?";
			
			ps = con.prepareStatement(query);
			
			try {
				ps.setString(1, newTransaction.get(choose-1).getTransactionId());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			newTransaction.remove(choose-1);
			
			System.out.println("Successfully delete transaction!");
		}
		
		Prompt();
	}
	
	public void View() {
		getTransaction();
		
		if(newTransaction.isEmpty() == true) {
			System.out.println("There is no transaction...");
		}else {
			printTransaction();
		}
		
		Prompt();
	}
	
	public void getTransaction() {
		newTransaction.clear();
		
		ps = con.prepareStatement((new Transaction("", "", "", 0)).selectAll());
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String transactionId = rs.getString(1);
				String drinkId = rs.getString(2);
				String buyerName = rs.getString(3);
				int quantity = rs.getInt(4);
				
				newTransaction.add(new Transaction(transactionId, drinkId, buyerName, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printTransaction() {
		System.out.println("==================================================================================================");
		System.out.println("|ID |Transaction ID |Buyer Name |Drink Name |Drink Type |Drink Price |Quantity |Tax |Total Price |");
		System.out.println("==================================================================================================");
		
		int idx = 1;
		
		for (Transaction i : newTransaction) {
			Drink j = getDrink(i.getDrinkId());
			
			if(j instanceof Tea) {
				System.out.println("|" + idx + " |" + i.getBuyerName() + " |" + j.getDrinkName() + " |" + j.getDrinkType() + " |" + j.getDrinkPrice() + " |" + i.getQuantity() + " |" + 2000 + " |" + ((i.getQuantity() * j.getDrinkPrice()) + 2000) + " |");
			}else if(j instanceof MilkTea) {
				System.out.println("|" + idx + " |" + i.getBuyerName() + " |" + j.getDrinkName() + " |" + j.getDrinkType() + " |" + j.getDrinkPrice() + " |" + i.getQuantity() + " |" + 3500 + " |" + ((i.getQuantity() * j.getDrinkPrice()) + 3500) + " |");				
			}
			
			idx++;
		}
		
		System.out.println("==================================================================================================");
	}
	
	public Drink getDrink(String id) {
		String query1 = "select * from Tea where drinkID = ?";
		
		ps = con.prepareStatement(query1);
		
		try {
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String drinkName = rs.getString(2);
				String drinkType = rs.getString(3);
				int drinkPrice = rs.getInt(4);
				String sugarType = rs.getString(5);
				
				return new Tea(id, drinkName, drinkType, drinkPrice, sugarType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String query2 = "select * from MilkTea where drinkID = ?";
		
		ps = con.prepareStatement(query2);
		
		try {
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String drinkName = rs.getString(2);
				String drinkType = rs.getString(3);
				int drinkPrice = rs.getInt(4);
				String milkType = rs.getString(5);
				
				return new MilkTea(id, drinkName, drinkType, drinkPrice, milkType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void Buy() {
		newDrink.clear();
		getTea();
		getMilkTea();
		printDrink();
		
		int choose;
		int quantity;
		String name;
		
		do {
			System.out.print("Input the number of index you want to buy[1 .. 10] : ");
			choose = scan.nextInt();
			scan.nextLine();
		} while (choose < 1 || choose > 10);
		
		do {
			System.out.print("Input quantity [>0] : ");
			quantity = scan.nextInt();
			scan.nextLine();
		} while (quantity <= 0);
		
		do {
			System.out.print("Input your name [must be 2 words] : ");
			name = scan.nextLine();
		} while (name.contains(" ") == false);
		
		int tax = 0;
		int price;
		
		if(newDrink.get(choose-1).getDrinkType().compareTo("Tea") == 0) {
			tax = 2000;
		}else if(newDrink.get(choose-1).getDrinkType().compareTo("Milk Tea") == 0) {
			tax = 3500;
		}
		
		price = (newDrink.get(choose-1).getDrinkPrice() * quantity) + tax;
		
		String id;
		int count = getLastId();
		
		if(count < 10) {
			id = String.format("TR00" + count);
		}else if(count < 100) {
			id = String.format("TR0" + count);
		}else {
			id = String.format("TR" + count);			
		}
		
		System.out.println("==================");
		System.out.println("Detail Transaction");
		System.out.println("==================");
		System.out.println("Transaction ID : " + id);
		System.out.println("Buyer Name : " + name);
		System.out.println("Drink Name : " + newDrink.get(choose-1).getDrinkName());
		System.out.println("Drink Price : " + newDrink.get(choose-1).getDrinkPrice());
		System.out.println("Drink Quantity : " + quantity);
		System.out.println("Tax : " + tax);
		System.out.println("Total Price : " + price);
		System.out.println("==================\n");
		
		String query = "insert into Transaction values (?, ?, ?, ?)";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, id);
			ps.setString(2, newDrink.get(choose-1).getDrinkId());
			ps.setString(3, name);
			ps.setInt(4, quantity);
			ps.executeUpdate();
			
			newTransaction.add(new Transaction(id, newDrink.get(choose-1).getDrinkId(), name, quantity));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Successfully inserted new transaction!");
		Prompt();
	}
	
	public Integer getLastId() {
		String query = "select TransactionID from Transaction order by TransactionID desc limit 1";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				rs.next();
				String id = rs.getString(1);
				id = id.substring(2);
				return Integer.parseInt(id) + 1;
			}else {
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -1;
		}
	}
	
	public void printDrink() {
		System.out.println("===========================================================================");
		System.out.println("|No |Drink ID |Drink Name |Drink Type |Drink Price |Sugar Type |Milk Type |");
		System.out.println("===========================================================================");
		
		int idx = 1;
		
		for (Drink i : newDrink) {
			if(i instanceof Tea) {
				System.out.println("|" + idx + " |" + i.getDrinkId() + " |" + i.getDrinkName() + " |" + i.getDrinkType() + " |" + i.getDrinkPrice() + " |" + ((Tea) i).getSugarType() + " | -  |");
			}else if(i instanceof MilkTea) {
				System.out.println("|" + idx + " |" + i.getDrinkId() + " |" + i.getDrinkName() + " |" + i.getDrinkType() + " |" + i.getDrinkPrice() + " | -  |" + ((MilkTea) i).getMilkType() + " |");
			}
			
			idx++;
		}
		
		System.out.println("===========================================================================");
	}
	
	public void getMilkTea() {
		ps = con.prepareStatement((new MilkTea("", "", "", 0, "")).selectAll());
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String drinkId = rs.getString(1);
				String drinkName = rs.getString(2);
				String drinkType = rs.getString(3);
				int drinkPrice = rs.getInt(4);
				String milkType = rs.getString(5);
				
				newDrink.add(new MilkTea(drinkId, drinkName, drinkType, drinkPrice, milkType));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getTea() {
		ps = con.prepareStatement((new Tea("", "", "", 0, "")).selectAll());
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String drinkId = rs.getString(1);
				String drinkName = rs.getString(2);
				String drinkType = rs.getString(3);
				int drinkPrice = rs.getInt(4);
				String sugarType = rs.getString(5);
				
				newDrink.add(new Tea(drinkId, drinkName, drinkType, drinkPrice, sugarType));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Prompt() {
		System.out.println("Press any key to continue...");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		Menu();
	}
	
	public void Choice(int menu) {
		switch (menu) {
			case 1:{
				Buy();
				
				break;
			}
			case 2:{
				View();
				
				break;
			}
			case 3:{
				Delete();
				
				break;
			}
			case 4:{
				System.out.println("Thank you for using Ngeteh Yuk! Application!");
				
				System.exit(0);
				
				break;
			}
		}
	}
	
	public void Menu() {
		System.out.println("Ngeteh Yuk!");
		System.out.println("===========");
		System.out.println("1. Buy Tea");
		System.out.println("2. View Transaction");
		System.out.println("3. Delete Transaction");
		System.out.println("4. Exit");
		
		int menu;
		
		do {
			System.out.print(">> ");
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
