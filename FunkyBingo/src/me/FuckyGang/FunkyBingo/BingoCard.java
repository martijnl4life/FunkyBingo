package me.FuckyGang.FunkyBingo;

import org.bukkit.NamespacedKey;

import hu.trigary.advancementcreator.Advancement;

public class BingoCard 
{
	private Advancement[] advancements;
	private Advancement root;
	private Advancement uninitialized;
	private int size;
	
	public BingoCard(Advancement[] advancements, Advancement root, Advancement uninitialized, int size)
	{
		this.advancements = advancements;
		this.root = root;
		this.uninitialized = uninitialized;
		this.size = size;
		
		createCard();
	}
	
	private void createCard()
	{
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
	}
	
	public void resetCard()
	{
		for (Advancement advancement : this.advancements)
		{
			advancement.makeChild(this.uninitialized.getId());
		}
	}

	private void addAdvancementToCard(Advancement advancement, NamespacedKey parent) 
	{
		advancement.makeChild(parent);
	}
}
