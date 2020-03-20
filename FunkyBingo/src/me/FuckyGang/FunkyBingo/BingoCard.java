package me.FuckyGang.FunkyBingo;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import hu.trigary.advancementcreator.Advancement;
import hu.trigary.advancementcreator.AdvancementFactory;

public class BingoCard 
{
	private AdvancementFactory factory;
	private final int size;
	
	public BingoCard(Plugin plugin)
	{
		this.factory = new AdvancementFactory(plugin, true, false);
		this.size = 3;
	}
	
	public void GenerateCard(ArrayList<AdvancementTemplate> ar)
	{
		Advancement prevAdv = null;
		Advancement root = null;
		
		root = factory.getRoot("bingo/root", "root", "Starting bitch", Material.ACACIA_BOAT, "block/dirt");
		
		for ( int y = 0; y < size; y ++)
		{
			for(int x = 0; x < size; x++)
			{
				AdvancementTemplate adv = ar.get(y * size + x);
				
				if (x == 0)
				{
					prevAdv = factory.getItem(adv.getId(), root, adv.getTitle(), adv.getDescription(), adv.getIcon(), adv.getAmount());
				}
				else if (prevAdv != null)
				{
					prevAdv = factory.getItem(adv.getId(), prevAdv, adv.getTitle(), adv.getDescription(), adv.getIcon(), adv.getAmount());
				}
			}
		}
	}
}
