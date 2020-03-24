package me.FuckyGang.FunkyBingo;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;

public class Bingo 
{
	
	private Player player;
	private Advancement[] advancements;
	
	public Bingo(Player player, Advancement[] card)
	{
		this.player = player;
		this.advancements = card;
	}
	
	public boolean checkBingo(Advancement[] card, int size)
	{
		return checkBingoHorizontal(size) || checkBingoVertical(size) || checkBingoDiagonal(size) || isCoverAll();
	}
	
	public boolean isCoverAll()
	{
		for (Advancement a : this.advancements)
		{
			if (!a.isGranted(player))
			{
				return false;
			}
		}
		return true;
	}

	public boolean checkBingoHorizontal(int size)
	{
		for (int i = 0; i < advancements.length - 1; i += size)
		{
			if (advancements[i].isGranted(this.player))
			{
				boolean bingo = true;
				for (int j = 0; j < size - 1; j++)
				{
					if (!advancements[i + j].isGranted(player))
					{
						bingo = false;
					}
				}
				if (bingo)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkBingoVertical(int size)
	{
		for (int i = 0; i < size - 1; i ++)
		{
			if(advancements[i].isGranted(this.player))
			{
				boolean bingo = true;
				for (int j = 0; j < advancements.length - 1; j += size)
				{
					if (!advancements[i + j].isGranted(player))
					{
						bingo = false;
					}
				}					
				if (bingo)
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkBingoDiagonal(int size)
	{
//		int[] i1 = {size*0 + 0, size*1 + 1, size*2 + 2}; diagonal: \
//		int[] i2 = {size*1 - 1, size*2 - 2, size*3 - 3}; diagonal: /
		for (int i = 0; i < size; i++)
		{
			if (!advancements[size * i + i].isGranted(player))
			{
				break;
			}
		}
	
		for (int i = 1; i < size + 1; i++)
		{
			if (!advancements[size * i - i].isGranted(player))
			{
				return false;
			}
		}
		
		return true;
	}
}
