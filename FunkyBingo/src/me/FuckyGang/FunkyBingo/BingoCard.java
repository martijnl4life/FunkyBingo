package me.FuckyGang.FunkyBingo;

import hu.trigary.advancementcreator.Advancement;

public class BingoCard 
{
	private Advancement[] advancements;
	private int size;
	
	public BingoCard(Advancement[] advancements, int size)
	{
		this.size = size;
		this.advancements = advancements;
		
		createCard();
	}
	
	private void createCard()
	{
		if (this.advancements.length != this.size * this.size)
		{
			System.out.println("not enough advancements in list");
		}
		else
		{
			int x = 0;
			int y = 0;
			for (int i = 1; i <= this.size; i++)
			{
				for (int j = 1; j <= this.size; j++)
				{
					addAdvancementToCard(this.advancements[i*j], x, y);
					x += 500;
					
				}
				y += 500;
			}
		}
	}

	private void addAdvancementToCard(Advancement advancement, int x, int y) 
	{
		
	}
}
