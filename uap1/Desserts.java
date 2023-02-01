package uap1;

public abstract class Desserts {

	private String DessertId;
	private String DessertName;
	private String DessertType;
	private int DessertPrice;
	
	public Desserts(String dessertId, String dessertName, String dessertType, int dessertPrice) {
		super();
		DessertId = dessertId;
		DessertName = dessertName;
		DessertType = dessertType;
		DessertPrice = dessertPrice;
	}

	public String getDessertId() {
		return DessertId;
	}

	public void setDessertId(String dessertId) {
		DessertId = dessertId;
	}

	public String getDessertName() {
		return DessertName;
	}

	public void setDessertName(String dessertName) {
		DessertName = dessertName;
	}

	public String getDessertType() {
		return DessertType;
	}

	public void setDessertType(String dessertType) {
		DessertType = dessertType;
	}

	public int getDessertPrice() {
		return DessertPrice;
	}

	public void setDessertPrice(int dessertPrice) {
		DessertPrice = dessertPrice;
	}
	
	public abstract String selectAll();
	
}
