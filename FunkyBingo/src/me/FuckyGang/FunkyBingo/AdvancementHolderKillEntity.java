package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AdvancementHolderKillEntity extends AdvancementHolder 
{
	private EntityType entity;
	
	protected AdvancementHolderKillEntity(int difficulty, String key, Material icon, String title, String description, EventType eventType, EntityType entity) 
	{
		super(difficulty, key, icon, title, description, 1, eventType);
		this.entity = entity;
	}
	
	public EntityType getEntity()
	{
		return this.entity;
	}
}
