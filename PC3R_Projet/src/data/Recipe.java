package data;

public class Recipe {
	
	private final Resource result;
	private final ResourceStack[] ingredients;
	
	Recipe(Resource result, ResourceStack[] ingredients) {
		this.result = result;
		this.ingredients = ingredients;
	}

	public Resource getResult() {
		return result;
	}

	public ResourceStack[] getIngredients() {
		return ingredients;
	}
	
	public String getInfo() {
		String result = "";
		for(ResourceStack ing : ingredients) {
			Resource r = ing.getResource();
			int c = ing.getCount();
			result += r.name() + "("+String.valueOf(c)+")";
		}
		System.out.println(result);
		return result;
	}
	
}
