package me.FuckyGang.FunkyBingo;

import java.util.Map;

import org.bukkit.Material;

public class AdvancementHolderInInventory extends AdvancementHolder 
{
	private Map<Material, Integer> materials;
	
	public AdvancementHolderInInventory(int difficulty, String key, Material icon, String title, String description, EventType eventType, Map<Material, Integer> mapMaterials)
	{
		super(difficulty, key, icon, title, description, 1, eventType);
		this.materials = mapMaterials;
	}

	public Map<Material, Integer> getMaterials() {
		return materials;
	}
	
	
}
