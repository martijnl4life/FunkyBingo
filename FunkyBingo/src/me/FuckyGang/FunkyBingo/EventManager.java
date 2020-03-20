package me.FuckyGang.FunkyBingo;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class EventManager implements Listener
{
	private ManagerInterface manager;
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
	}
	
	@EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event)
	{
		if (event.getItem().getItemStack().getType() == Material.DIAMOND_BLOCK)
		{
			if (event.getEntity() instanceof Player)
			{
				advance((Player)event.getEntity(), manager.getManager().getAdvancement(new NameKey("bingo", "diamondblock4")));
			}
		}
	}
	
	public void advance(Player player, Advancement advancement)
	{
		int progress = manager.getManager().getCriteriaProgress(player, advancement);
		if (progress < advancement.getCriteria())
		{
			manager.getManager().setCriteriaProgress(player, advancement, progress + 1);
		}
	}
}
