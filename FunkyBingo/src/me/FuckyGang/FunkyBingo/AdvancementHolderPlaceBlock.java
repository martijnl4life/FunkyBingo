package me.FuckyGang.FunkyBingo;

import org.bukkit.Location;
import org.bukkit.Material;

public class AdvancementHolderPlaceBlock extends AdvancementHolder
{
	private Pair<Material, Location> block;
	
	protected AdvancementHolderPlaceBlock(int difficulty, String key, Material icon, String title, String description, EventType eventType, Pair<Material, Location> block) {
		super(difficulty, key, icon, title, description, 1, eventType);
		this.block = block;
	}
	
	public Pair<Material, Location> getBlock()
	{
		return this.block;
	}
	
}
