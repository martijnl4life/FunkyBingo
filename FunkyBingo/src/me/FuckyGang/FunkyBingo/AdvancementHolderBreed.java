package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.Animals;

public class AdvancementHolderBreed extends AdvancementHolder {

	private Animals animal;
	
	protected AdvancementHolderBreed(int difficulty, String key, Material icon, String title, String description,
			Animals animal) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_BRED);
		this.animal = animal;
		
	}

	public Animals getAnimals()
	{
		return animal;
	}
	
}
