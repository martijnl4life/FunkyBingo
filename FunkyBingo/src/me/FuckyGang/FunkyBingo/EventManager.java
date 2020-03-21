package me.FuckyGang.FunkyBingo;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;

public class EventManager implements Listener
{
	private ManagerInterface manager;
	private Set<String> namespaces;
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
		this.namespaces = manager.getNamespaces();
	}
	
	@EventHandler
	public void onEntityPickupItemEvent(InventoryClickEvent event)
	{
		if(event.getInventory().contains(Material.DIAMOND_BLOCK))
		{
			check((Player)event.getWhoClicked(),"diamondblock");
		}
	}
	
	private void check(Player player, String advancementKey)
	{
		for (String namespace : namespaces)
		{
			if (manager.getManager(namespace).hasPlayerInList(player.getUniqueId()))
			{
				advance(player,namespace, manager.getManager(namespace).getAdvancementManager().getAdvancement(generateNameKey(namespace, advancementKey)));
			}
		}
	}
	
	private void advance(Player player,String namespace, Advancement advancement)
	{
		int progress = manager.getManager(namespace).getAdvancementManager().getCriteriaProgress(player, advancement);
		if (progress < advancement.getCriteria())
		{
			manager.getManager(namespace).getAdvancementManager().setCriteriaProgress(player, advancement, progress + 1);
		}
	}
	
	private NameKey generateNameKey(String namespace, String key)
	{
		return new NameKey(namespace, key);
	}
}
