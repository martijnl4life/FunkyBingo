package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;
import net.md_5.bungee.api.ChatColor;

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
			try {
				Map<Material, Integer> temp = ((AdvancementHolderInInventory)ah).getMaterials();
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
			catch (Exception e)
			{
				Bukkit.getLogger().log(Level.SEVERE, e.getMessage() + " " + ah.getKey() + " " + "something went wrong here");
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void OnItemConsumeEvent(PlayerItemConsumeEvent event)
	{
		for (AdvancementHolder ah : itemConsumed)
		{
			try {
				ArrayList<Material> temp = ((AdvancementHolderConsumables)ah).getConsumables();
				ArrayList<Pair<UUID, Material>> consumed = ((AdvancementHolderConsumables)ah).getPlayersHaveConsumed();
				
				Bukkit.getLogger().log(Level.INFO,"Size of material array: " + temp.size() + " " + "Size of Pair array " + consumed.size() + "");
				boolean hasConsumed = false;
				for (Material material : temp)
				{
					if (event.getItem().getType().equals(material))
					{
						if(consumed.contains(Pair.of(event.getPlayer().getUniqueId(), material)))
						{
							hasConsumed = true;
						}
						{
							((AdvancementHolderConsumables)ah).addPlayerConsumable(Pair.of(event.getPlayer().getUniqueId(), material));
						}
						break;
					}
				}
				if (!hasConsumed)
				{
					check((Player)event.getPlayer(),ah.getKey());
				}
			}
			catch (Exception e)
			{
				Bukkit.getLogger().log(Level.SEVERE, e.getMessage() + " " + ah.getKey() + " " + "something went wrong here");
				e.printStackTrace();
			}
		}
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
					int size = manager.getManager(namespace).getSize();
					if (Bingo.checkBingo(players.get(i), 
									manager.getManager(namespace).getAdvancementManager().getAdvancements(namespace).toArray(new Advancement[size * size + 1]),
									size))
					{
						player.sendMessage(ChatColor.DARK_AQUA + "BITCH, BINGO");
					}
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
