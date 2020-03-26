package me.FuckyGang.FunkyBingo;

import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;

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
	public static void checkBingo(Player player, Advancement[] card, int size, AdvancementManagerInstance am)
	{
		Advancement[] temp = new Advancement[size*size];
		for (int i = 1; i < size * size + 1; i++)
		{
			temp[i - 1] = card[i];
		}
		checkBingoRow(player,temp,size,am);
		checkBingoColumn(player,temp,size,am);
		checkBingoDiagonalUp(player,temp,size,am);
		checkBingoDiagonalDown(player,temp,size,am);
	}

	private static void checkBingoRow(Player player, Advancement[] card, int size, AdvancementManagerInstance am)
	{
		for (int y = 0; y < size; y++)
		{
			boolean bingo = true;
			for (int x = 0; x < size; x++)
			{
				if (!card[x + size * y].isGranted(player) || am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-row-" + Integer.toString(y))).isGranted(player))
				{
					bingo = false;
					break;
				}
			}
			if (bingo)
			{
				am.getAdvancementManager().grantAdvancement(player, am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-row-" + Integer.toString(y))));
			}
		}
	}
	
	private static void checkBingoColumn(Player player, Advancement[] card, int size, AdvancementManagerInstance am)
	{
		for (int x = 0; x < size; x++)
		{
			boolean bingo = true;
			for (int y = 0; y < size; y++)
			{
				if (!card[x + size * y].isGranted(player) || am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-column-" + Integer.toString(x))).isGranted(player))
				{
					bingo = false;
					break;
				}
			}
			if (bingo)
			{
				am.getAdvancementManager().grantAdvancement(player, am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-column-" + Integer.toString(x))));
			}
		}
	}

	private static void checkBingoDiagonalUp(Player player, Advancement[] card, int size,AdvancementManagerInstance am)
	{
		for (int i = 0; i < size; i++)
		{
			if (!card[size * i + i].isGranted(player) || am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-diagonal-up")).isGranted(player))
			{
				return;
			}
		}
		am.getAdvancementManager().grantAdvancement(player, am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-diagonal-up")));;
	}
	
	private static void checkBingoDiagonalDown(Player player, Advancement[] card, int size,AdvancementManagerInstance am)
	{
		for (int i = 1; i < size + 1; i++)
		{
			if (!card[size * i + i].isGranted(player) || am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-diagonal-down")).isGranted(player))
			{
				return ;
			}
		}
		am.getAdvancementManager().grantAdvancement(player, am.getAdvancementManager().getAdvancement(new NameKey(am.getId(),"bingo-diagonal-down")));
	}
}
