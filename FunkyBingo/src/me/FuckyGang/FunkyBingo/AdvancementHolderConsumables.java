package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;

public class AdvancementHolderConsumables extends AdvancementHolder 
{
	private ArrayList<Material> consumables;
	private ArrayList<Pair<UUID, Material>> playersHaveConsumed;
	
	public AdvancementHolderConsumables(int difficulty, String key, Material icon, String title, String description, EventType eventType, Material... consumables)
	{
		super(difficulty, key, icon, title, description, consumables.length, eventType);
		this.consumables = new ArrayList<Material>();
		this.playersHaveConsumed = new ArrayList<Pair<UUID, Material>>();
		for (Material m : consumables)
		{
			this.consumables.add(m);
		}
	}

	public ArrayList<Material> getConsumables() {
		return consumables;
	}

	public ArrayList<Pair<UUID, Material>> getPlayersHaveConsumed() {
		return playersHaveConsumed;
	}
	
	public void addPlayerConsumable(Pair<UUID, Material> p)
	{
		if (!playersHaveConsumed.contains(p))
		{
			playersHaveConsumed.add(p);
		}
	}
}
