package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AdvancementHolderDeathBy extends AdvancementHolder {
	
	private EntityType entityType;
	
	protected AdvancementHolderDeathBy(int difficulty, String key, Material icon, String title, String description,
			EntityType entityType) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_DIED);
		this.entityType = entityType;
	}

	public EntityType getEntityType()
	{
		return entityType;
	}
}
