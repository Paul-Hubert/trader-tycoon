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
	
}
