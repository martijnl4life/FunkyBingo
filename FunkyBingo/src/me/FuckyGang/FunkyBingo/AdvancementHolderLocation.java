package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.StructureType;

public class AdvancementHolderLocation extends AdvancementHolder {

	private StructureType structureType;
	
	protected AdvancementHolderLocation(int difficulty, String key, Material icon, String title, String description,
			 StructureType structureType) {
		super(difficulty, key, icon, title, description, 1, EventType.IS_SOMEWHERE );
		this.structureType = structureType;
	}
	
	public StructureType getStructureType()
	{
		return structureType;
	}

}
