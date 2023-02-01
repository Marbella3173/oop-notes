package uap1;

public class IceCreams extends Desserts {
	
	Connect con = Connect.getConnection();

	private String CreamName;

	public IceCreams(String dessertId, String dessertName, String dessertType, int dessertPrice, String creamName) {
		super(dessertId, dessertName, dessertType, dessertPrice);
		CreamName = creamName;
	}

	public String getCreamName() {
		return CreamName;
	}

	public void setCreamName(String creamName) {
		CreamName = creamName;
	}
	
	@Override
	public String selectAll() {
		// TODO Auto-generated method stub
		
		String query = "SELECT * FROM IceCreams";
		
		return query;
		
	}
	
}
