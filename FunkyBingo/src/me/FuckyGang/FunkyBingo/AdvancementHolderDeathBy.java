package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

public class AdvancementHolderDeathBy extends AdvancementHolder {
	
	private LivingEntity livingEntity;
	
	protected AdvancementHolderDeathBy(int difficulty, String key, Material icon, String title, String description,
			 LivingEntity livingEntity) {
		super(difficulty, key, icon, title, description, 1, EventType.HAS_DIED);
		this.livingEntity = livingEntity;
	}

	public LivingEntity getLivingEntity()
	{
		return livingEntity;
	}
}
