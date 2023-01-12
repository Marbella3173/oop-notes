package uap2;

// encapsulation, inheritance, polymorphism

public class Tea extends Drink {

	private String sugarType;

	public Tea(String drinkId, String drinkName, String drinkType, int drinkPrice, String sugarType) {
		super(drinkId, drinkName, drinkType, drinkPrice);
		this.sugarType = sugarType;
	}

	public String getSugarType() {
		return sugarType;
	}

	public void setSugarType(String sugarType) {
		this.sugarType = sugarType;
	}

	@Override
	public String selectAll() {
		// TODO Auto-generated method stub
		String query = "select * from Tea";
		
		return query;
	}
	
}
