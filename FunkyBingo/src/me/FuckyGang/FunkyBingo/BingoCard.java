package me.FuckyGang.FunkyBingo;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import hu.trigary.advancementcreator.Advancement;
import hu.trigary.advancementcreator.AdvancementFactory;

public class BingoCard 
{
	private AdvancementFactory factory;
	
	public BingoCard(Plugin plugin)
	{
		factory = new AdvancementFactory(plugin, true, false);
	}
	
}
