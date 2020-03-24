package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;

public class EventManager implements Listener
{
	private ManagerInterface manager;
	private Set<String> namespaces;
	private ArrayList<AdvancementHolder> inInventory;
	private ArrayList<AdvancementHolder> itemConsumed;
	
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
		this.namespaces = manager.getNamespaces();
		this.inInventory = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.IN_INVENTORY));
		this.itemConsumed = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_CONSUMED));

	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event)
	{
		for (AdvancementHolder ah : inInventory)
		{
			Map<Material, Integer> temp = ah.getMaterials();
			boolean hasAllItems = true;
			for (Map.Entry<Material, Integer> entry : temp.entrySet())
			{
				if(!event.getPlayer().getInventory().contains(entry.getKey(), entry.getValue()))
				{
					hasAllItems = false;
					break;
				}
			}
			if (hasAllItems)
			{
				check((Player)event.getPlayer(),ah.getKey());
			}
		}
	}
	
	@EventHandler
	public void OnItemConsumeEvent(PlayerItemConsumeEvent event)
	{
	}
	
	private void check(Player player, String advancementKey)
	{
		for (String namespace : namespaces)
		{
			if (manager.getManager(namespace).hasPlayerInListOrTeam(player.getUniqueId()))
			{
				ArrayList<Player> players = manager.getManager(namespace).getTeamMembers(player);
				for (int i = 0; i< players.size(); i++)
				{
					advance(players.get(i),namespace, manager.getManager(namespace).getAdvancementManager().getAdvancement(generateNameKey(namespace, advancementKey)));
				}
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
