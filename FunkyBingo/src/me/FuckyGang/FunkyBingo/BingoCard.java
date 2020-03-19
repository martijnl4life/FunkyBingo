package me.FuckyGang.FunkyBingo;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import hu.trigary.advancementcreator.Advancement;

public class BingoCard 
{
	private Advancement[] advancements;
	private Advancement root;
	private Advancement uninitialised;
	private int size;
	private boolean created;
	
	public BingoCard(Advancement root, Advancement uninitialised)
	{
		this.root = root;
		this.uninitialised = uninitialised;
		this.created = false;
	}
	
	public void createCard(Advancement[] advancements, int size)
	{
		this.advancements = advancements;
		this.size = size;
		NamespacedKey parent;
		for (int x = 0; x < this.size; x++)
		{
			for (int y = 0; y < this.size; y++)
			{
				if (x == 0)
				{
					parent = this.root.getId();
				}
				else
				{
					parent = this.advancements[y * this.size + x - 1].getId();
				}
				
				this.advancements[y * this.size + x].makeChild(parent);
			}
		}
		this.created = true;
		Bukkit.reloadData();
		Bukkit.getLogger().log(Level.INFO, Integer.toString(size));
	}
	
	public void resetCard()
	{
		if (isCreated())
		{
			for (Advancement advancement : this.advancements)
			{
				advancement.makeChild(this.uninitialised.getId());
				advancement.setHidden(true);
			}
			this.created = false;
			Bukkit.reloadData();
		}
	}
	
	public boolean isCreated()
	{
		return created;
	}
}
