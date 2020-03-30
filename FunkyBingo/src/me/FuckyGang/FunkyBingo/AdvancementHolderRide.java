package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

public class AdvancementHolderRide extends AdvancementHolder {

	private Entity entity;
	
	protected AdvancementHolderRide(int difficulty, String key, Material icon, String title, String description,
			 Entity entity) {
		super(difficulty, key, icon, title, description, 1, EventType.IS_RIDING);
		this.entity = entity;
	}
	
	public Entity getEntity()
	{
		return entity;
	}

}
