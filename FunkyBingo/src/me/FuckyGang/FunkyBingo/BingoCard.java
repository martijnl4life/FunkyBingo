package me.FuckyGang.FunkyBingo;

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
		this.uninitialised.setHidden(true);
		this.created = false;
	}
	
	public void createCard(Advancement[] advancements, int size)
	{
		this.advancements = advancements;
		this.size = size;
		NamespacedKey parent;
		for (int i = 1; i <= this.size; i++)
		{
			for (int j = 1; j <= this.size; j++)
			{
				if (j == 0)
				{
					parent = this.root.getId();
				}
				else
				{
					parent = this.advancements[i * j - 1].getId();
				}
				
				addAdvancementToCard(this.advancements[i*j], parent);
			}
		}
		this.created = true;
		Bukkit.reloadData();
	}
	
	public void resetCard()
	{
		for (Advancement advancement : this.advancements)
		{
			advancement.makeChild(this.uninitialised.getId());
			advancement.setHidden(true);
		}
		this.created = false;
		Bukkit.reloadData();
	}
	
	public boolean isCreated()
	{
		return created;
	}

	private void addAdvancementToCard(Advancement advancement, NamespacedKey parent) 
	{
		advancement.makeChild(parent);
	}
}
