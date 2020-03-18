package me.FuckyGang.FunkyBingo;

import org.bukkit.plugin.Plugin;
import hu.trigary.advancementcreator.AdvancementFactory;
import hu.trigary.advancementcreator.Advancement;

public class AdvancementManager {

	
	private AdvancementFactory factory;
	private Advancement[] advancements;
	
	
	public AdvancementManager(Plugin plugin)
	{
		this.factory = new AdvancementFactory(plugin, true, false);
	}
}
