package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;

public class AdvancementHolderBrew extends AdvancementHolder {

	private Material material;
	
	protected AdvancementHolderBrew(int difficulty, String key, Material icon, String title, String description,
			 Material material) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_BREWED);
		this.material = material;
	}

	public Material getMaterial() 
	{
		return material;
	}
}
