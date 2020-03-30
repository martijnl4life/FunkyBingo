package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;

public class AdvancementHolderCraft extends AdvancementHolder {

	private Material material;
	
	protected AdvancementHolderCraft(int difficulty, String key, Material icon, String title, String description,
			 Material material) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_CRAFTED);
		// TODO Auto-generated constructor stub
		
	}
	
	public Material getMaterial()
	{
		return material;
	}

}
