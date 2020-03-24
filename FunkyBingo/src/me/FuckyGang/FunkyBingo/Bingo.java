package me.FuckyGang.FunkyBingo;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;

public class Bingo 
{
	public static boolean checkBingo(Player player, Advancement[] card, int size)
	{
		return checkBingoHorizontal(player, card, size) || checkBingoVertical(player, card, size) || checkBingoDiagonal(player, card, size) || isCoverAll(player, card);
	}
	
	public static boolean isCoverAll(Player player, Advancement[] card)
	{
		for (Advancement a : card)
		{
			if (!a.isGranted(player))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean checkBingoHorizontal(Player player, Advancement[] card, int size)
	{
		for (int i = 0; i < card.length - 1; i += size)
		{
			if (card[i].isGranted(player))
			{
				boolean bingo = true;
				for (int j = 0; j < size - 1; j++)
				{
					if (!card[i + j].isGranted(player))
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
	
	private static boolean checkBingoVertical(Player player, Advancement[] card, int size)
	{
		for (int i = 0; i < size - 1; i ++)
		{
			if(card[i].isGranted(player))
			{
				boolean bingo = true;
				for (int j = 0; j < card.length - 1; j += size)
				{
					if (!card[i + j].isGranted(player))
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

	private static boolean checkBingoDiagonal(Player player, Advancement[] card, int size)
	{
		for (int i = 0; i < size; i++)
		{
			if (!card[size * i + i].isGranted(player))
			{
				break;
			}
		}
	
		for (int i = 1; i < size + 1; i++)
		{
			if (!card[size * i - i].isGranted(player))
			{
				return false;
			}
		}
		
		return true;
	}
}
