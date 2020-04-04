package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AdvancementHolderBreed extends AdvancementHolder {

	private EntityType entityType;
	
	protected AdvancementHolderBreed(int difficulty, String key, Material icon, String title, String description,
			EntityType entityType) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_BRED);
		this.entityType = entityType;
		
	}

	public EntityType getType()
	{
		return entityType;
	}
	
}
