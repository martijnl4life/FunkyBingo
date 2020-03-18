package me.FuckyGang.FunkyBingo;

import hu.trigary.advancementcreator.Advancement;

public class BingoCard 
{
	private Advancement[] advancements;
	private Advancement root;
	private int size;
	
	public BingoCard(Advancement[] advancements, Advancement root, int size)
	{
		this.advancements = advancements;
		this.root = root;
		this.size = size;
		
		createCard();
	}
	
	private void createCard()
	{
		Advancement parent;
		for (int i = 1; i <= this.size; i++)
		{
			for (int j = 1; j <= this.size; j++)
			{
				if (j == 0)
				{
					parent = this.root;
				}
				else
				{
					parent = this.advancements[i * j - 1];
				}
				addAdvancementToCard(this.advancements[i*j], parent);
			}
		}
	}

	private void addAdvancementToCard(Advancement advancement, Advancement parent) 
	{
		
	}
}
