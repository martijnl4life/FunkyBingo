package me.FuckyGang.FunkyBingo;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerEvent;

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
	public void onEntityPickupItemEvent(InventoryOpenEvent event)
	{
		if(event.getPlayer().getInventory().contains(Material.DIAMOND_BLOCK))
		{
			check((Player)event.getPlayer(),"diamondblock");
		}
		if(event.getPlayer().getInventory().contains(Material.BOOKSHELF))
		{
			check((Player)event.getPlayer(),"bookshelf");
		}
		if(event.getPlayer().getInventory().contains(Material.ENCHANTING_TABLE))
		{
			check((Player)event.getPlayer(),"enchantmenttable");
		}
		if(event.getPlayer().getInventory().contains(Material.END_CRYSTAL))
		{
			check((Player)event.getPlayer(),"endcrystal");
		}
		if(event.getPlayer().getInventory().contains(Material.EMERALD_BLOCK))
		{
			check((Player)event.getPlayer(),"emeraldblock");
		}
		if(event.getPlayer().getInventory().contains(Material.GLISTERING_MELON_SLICE))
		{
			check((Player)event.getPlayer(),"glisteringmelonslice");
		}
		if(event.getPlayer().getInventory().contains(Material.COOKIE))
		{
			check((Player)event.getPlayer(),"cookie");
		}
		if(event.getPlayer().getInventory().contains(Material.BRICK, 64))
		{
			check((Player)event.getPlayer(),"brick");
		}
		if(event.getPlayer().getInventory().contains(Material.SEA_PICKLE, 32))
		{
			check((Player)event.getPlayer(),"seapickle");
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
