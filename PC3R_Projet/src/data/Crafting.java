package data;

import java.util.HashMap;
import java.util.Map;

import static data.Resource.*;

public abstract class Crafting {
	
	public static final Map<Resource, Recipe> recipes = new HashMap<>();
	
	private static void addRecipe(Recipe recipe) {
		recipes.put(recipe.getResult(), recipe);
	}
	
	static {
		
		addRecipe(new Recipe(bread, new ResourceStack[] {
				new ResourceStack(wheat, 2)
		}));
		
	}
	
}
