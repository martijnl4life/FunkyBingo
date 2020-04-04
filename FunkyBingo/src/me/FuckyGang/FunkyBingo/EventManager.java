package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;

public class EventManager implements Listener
{
	private ManagerInterface manager;
	private Set<String> namespaces;
	private ArrayList<AdvancementHolder> inInventory;
	private ArrayList<AdvancementHolder> itemConsumed;
	private ArrayList<AdvancementHolder> blockPlaced;
	private ArrayList<AdvancementHolder> killedEntity;
	private ArrayList<AdvancementHolder> itemCrafted;
	private ArrayList<AdvancementHolder> itemBrewed;
	private ArrayList<AdvancementHolder> hasDied;
	private ArrayList<AdvancementHolder> isSomewhere;
	private ArrayList<AdvancementHolder> isRiding;
	private ArrayList<AdvancementHolder> itemUsed;
	private ArrayList<AdvancementHolder> hasBred;
	
	
	public EventManager(ManagerInterface manager)
	{
		this.manager = manager;
		this.namespaces = manager.getNamespaces();
		
		this.inInventory = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.IN_INVENTORY));
		this.itemConsumed = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_CONSUMED));
		this.blockPlaced = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.BLOCK_PLACED));
		this.killedEntity = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.KILLED_ENTITY));
		this.itemCrafted = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_CRAFTED));
		this.itemBrewed = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_BREWED));
		this.hasDied = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_DIED));
		this.isSomewhere = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.IS_SOMEWHERE));
		this.isRiding = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.IS_SOMEWHERE));
		this.itemUsed = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_USED));
		this.hasBred = new ArrayList<AdvancementHolder>(manager.getHolders(EventType.HAS_BRED));
	}
	
	@EventHandler
	public void onEntityMountEvent(VehicleMoveEvent event)
	{
		for (AdvancementHolder ah : this.isRiding)
		{
			try {
				EntityType entityType = ((AdvancementHolderRide)ah).getEntityType();
				if (((Entity)event.getVehicle()).getType() == entityType)
				{
					for (Entity e : event.getVehicle().getPassengers())
					{
						if (e instanceof Player)
						{
							check((Player)e, ah.getKey());
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
	
	@EventHandler
	public void onEntityBreedEvent(EntityBreedEvent event)
	{
		for (AdvancementHolder ah : this.hasBred)
		{
			try {
				EntityType entity = ((AdvancementHolderBreed)ah).getType();
				if (entity == event.getEntity().getType())
				{
					check((Player)event.getBreeder(), ah.getKey());
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
	public void onPotionBrewEvent(BrewEvent event)
	{
		for (AdvancementHolder ah : this.itemBrewed)
		{
			try {
				PotionType potionType = ((AdvancementHolderBrew)ah).getPotionType();
				ItemStack[] itemStackArrayBrewingStand = event.getContents().getStorageContents();
				for (ItemStack is : itemStackArrayBrewingStand)
				{
					if (is.getItemMeta() instanceof PotionMeta)
					{
						PotionMeta meta = (PotionMeta)is.getItemMeta();
						if (meta.getBasePotionData().getType() == potionType)
						{
							Iterator<Entity> it = event.getBlock().getWorld().getNearbyEntities(event.getBlock().getBoundingBox().expand(7)).iterator();
							while (it.hasNext())
							{
								Entity entity = it.next();
								if (entity instanceof Player)
								{
									check((Player)entity, ah.getKey());
								}
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
				if (hasAllItems && ah != null)
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
				Pair<Material, Location> block = ((AdvancementHolderPlaceBlock)ah).getBlock();
				
				if (event.getBlock().getType() == block.first)
				{
					Location location = event.getBlock().getLocation();
					location.setWorld(event.getPlayer().getWorld());
					Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("Location: " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
					
					if (location.getBlockX() == block.second.getBlockX()) 
					{
						if (location.getBlockY() == block.second.getBlockY()) 
						{
							if (location.getBlockZ() == block.second.getBlockZ()) 
							{
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
	
	@EventHandler
	public void onEntityKilledEvent(EntityDeathEvent event)
	{
		if (event.getEntity().getKiller() == null)
		{
			return;
		}
		for (AdvancementHolder ah : this.killedEntity)
		{
			try
			{
				EntityType entity = ((AdvancementHolderKillEntity) ah).getEntity();

				Player player = event.getEntity().getKiller();
				if (event.getEntity().getType().equals(entity))
				{
					check(player, ah.getKey());
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
	private void onCraftItemEvent(CraftItemEvent event)
	{
		for (AdvancementHolder ah : this.itemCrafted)
		{
			try
			{
				Material item = ((AdvancementHolderCraft)ah).getMaterial();
				
				Player player = (Player)event.getWhoClicked();
				if (event.getInventory().getResult().getType() == item)
				{
					check(player, ah.getKey());
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
	private void onPlayerDeathEvent(PlayerDeathEvent event)
	{
		for (AdvancementHolder ah : this.hasDied)
		{
			try
			{
				EntityType entity = ((AdvancementHolderDeathBy)ah).getEntityType();
				
				Player player = event.getEntity();
				
				if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent)
				{
					EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)player.getLastDamageCause();
					if (e.getDamager() instanceof LivingEntity)
					{
						LivingEntity damager = (LivingEntity)e.getDamager();
						
						if(damager.getType().equals(entity))
						{
							check(player, ah.getKey());
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
					Advancement adv = manager.getManager(namespace).getAdvancementManager().getAdvancement(generateNameKey(namespace, advancementKey));
					if (adv != null)
					{
						advance(players.get(i),namespace, adv);
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
