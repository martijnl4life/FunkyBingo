package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
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
	private ArrayList<AdvancementHolder> blockPlaced;
	
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
		this.namespaces = manager.getNamespaces();
		this.inInventory = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.IN_INVENTORY));
		this.itemConsumed = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_CONSUMED));
		this.blockPlaced = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.BLOCK_PLACED));

	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event)
	{
		for (AdvancementHolder ah : this.inInventory)
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
		for (AdvancementHolder ah : this.itemConsumed)
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
						else 
						{
							((AdvancementHolderConsumables)ah).addPlayerConsumable(Pair.of(event.getPlayer().getUniqueId(), material));
						}
						break;
					}
				}
				if (!hasConsumed && consumed.contains(Pair.of(event.getPlayer().getUniqueId(), event.getItem().getType())))
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
	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		for (AdvancementHolder ah : this.blockPlaced)
		{
			try
			{
				Pair<Material, Position> block = ((AdvancementHolderPlaceBlock)ah).getBlock();
				
				if (event.getBlock().getType() == block.first)
				{
					Location location = event.getBlock().getLocation();
					location.setWorld(event.getPlayer().getWorld());
					
					if (location.getBlockX() == block.second.getBlockPos().getBlockX() && block.second.isImportantX()) 
					{
						Bukkit.getLogger().log(Level.SEVERE, "locationX is gucci");
						if (location.getBlockY() == block.second.getBlockPos().getBlockY() && block.second.isImportantY()) 
						{
							Bukkit.getLogger().log(Level.SEVERE, "locationX is gucci");
							if (location.getBlockZ() == block.second.getBlockPos().getBlockZ() && block.second.isImportantZ()) 
							{
								Bukkit.getLogger().log(Level.SEVERE, "locationX is gucci");
								check(event.getPlayer(),ah.getKey());
							}
						}
					}
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
		onBingoGrantEvent(player);
	}
	
	private NameKey generateNameKey(String namespace, String key)
	{
		return new NameKey(namespace, key);
	}
	
	public void onBingoGrantEvent(Player player)
	{
		for (String namespace : namespaces)
		{
			int size = manager.getManager(namespace).getSize();
			Bingo.checkBingo(player, 
					manager.getManager(namespace).getAdvancementManager().getAdvancements(namespace).toArray(new Advancement[size * size + 1 + size + size + 2]),
					size, manager.getManager(namespace));
		}
	}
	
}
