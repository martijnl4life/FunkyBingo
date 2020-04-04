package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class AdvancementHolderBrew extends AdvancementHolder {

	private PotionType input;
	private Material ingredient;
	
	protected AdvancementHolderBrew(int difficulty, String key, Material icon, String title, String description, PotionType input, Material ingredient) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_BREWED);
		this.input = input;
		this.ingredient = ingredient;
	}

	public PotionType getInput() 
	{
		return input;
	}
	
	public Material getIngredient() 
	{
		return ingredient;
	}
}
