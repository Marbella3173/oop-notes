package uap1;

public class Yoghurts extends Desserts {
	
	Connect con = Connect.getConnection();

	private String ProbioticName;

	public Yoghurts(String dessertId, String dessertName, String dessertType, int dessertPrice, String probioticName) {
		super(dessertId, dessertName, dessertType, dessertPrice);
		ProbioticName = probioticName;
	}

	public String getProbioticName() {
		return ProbioticName;
	}

	public void setProbioticName(String probioticName) {
		ProbioticName = probioticName;
	}

	@Override
	public String selectAll() {
		// TODO Auto-generated method stub
		
		String query = "SELECT * FROM Yoghurts";
		
		return query;
		
	}
	
}
