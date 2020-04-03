package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AdvancementHolderRide extends AdvancementHolder {

	private EntityType entityType;
	
	protected AdvancementHolderRide(int difficulty, String key, Material icon, String title, String description,
			 EntityType entityType) {
		super(difficulty, key, icon, title, description, 1, EventType.IS_RIDING);
		this.entityType = entityType;
	}
	
	public EntityType getEntityType()
	{
		return entityType;
	}

}
