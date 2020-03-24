package me.FuckyGang.FunkyBingo;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;

public class Bingo 
{
	/* 0 = no bingo
 	 * 1 = diagonal \
	 * 2 = diagonal /
	 * 0 + 2 + 1*i = row i
	 * 0 + 2 + 1*i + size = coll i
	 * coll i = row i + size
	 * 
	 * 0 + 2 + size + size = volle kaart
	 * */
	public static boolean checkBingo(Player player, Advancement[] card, int size)
	{
		Advancement[] temp = new Advancement[size*size];
		for (int i = 1; i < card.length; i++)
		{
			temp[i - 1] = card[i];
		}
		
		return checkBingoHorizontal(player, temp, size) || checkBingoVertical(player, temp, size) || checkBingoDiagonal(player, temp, size) || isCoverAll(player, temp);
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
