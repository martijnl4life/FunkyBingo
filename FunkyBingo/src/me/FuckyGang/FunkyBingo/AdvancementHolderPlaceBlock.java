package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;

public class AdvancementHolderPlaceBlock extends  AdvancementHolder
{
	private Pair<Material, Position> block;
	
	protected AdvancementHolderPlaceBlock(int difficulty, String key, Material icon, String title, String description, EventType eventType, Position pos) {
		super(difficulty, key, icon, title, description, 1, eventType);
		this.block = Pair.of(icon, pos);
	}
	
	public Pair<Material, Position> getBlock()
	{
		return this.block;
	}
	
}
