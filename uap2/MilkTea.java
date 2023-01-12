package uap2;

//encapsulation, inheritance, polymorphism

public class MilkTea extends Drink {

	private String milkType;

	public MilkTea(String drinkId, String drinkName, String drinkType, int drinkPrice, String milkType) {
		super(drinkId, drinkName, drinkType, drinkPrice);
		this.milkType = milkType;
	}

	public String getMilkType() {
		return milkType;
	}

	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}

	@Override
	public String selectAll() {
		// TODO Auto-generated method stub
		String query = "select * from MilkTea";
		
		return query;
	}
	
}
