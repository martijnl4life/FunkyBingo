package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class AdvancementHolderBrew extends AdvancementHolder {

	private PotionType potionType;
	
	protected AdvancementHolderBrew(int difficulty, String key, Material icon, String title, String description,
			 PotionType potionType) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_BREWED);
		this.potionType = potionType;
	}

	public PotionType getPotionType() 
	{
		return potionType;
	}
}
