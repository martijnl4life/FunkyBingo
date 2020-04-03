package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Manager implements ManagerInterface
{
	private Map<String, AdvancementManagerInstance> managerList;
	private ArrayList<AdvancementHolder> holders;
	
	public Manager()
	{
		this.managerList = new HashMap<String, AdvancementManagerInstance>();
		this.holders = new ArrayList<AdvancementHolder>();
		initAdvancements();

	}
	
	@Override
	public void addPlayer(String id, Player player)
	{
		if (managerList.containsKey(id))
		{
			getManager(id).addPlayer(player);
		}
	}
	
	@Override 
	public void removePlayer(String id, Player player)
	{
		if (managerList.containsKey(id))
		{
			getManager(id).removePlayer(player);
		}
	}
	
	@Override
	public boolean createCard(String id, int difficulty, int size)
	{
		this.managerList.put(id,new AdvancementManagerInstance(id) );
		this.managerList.get(id).setSize(size);
		
		ArrayList<AdvancementHolder> ahaDifficulties = new ArrayList<AdvancementHolder>();
		ArrayList<Integer> difficulties = new ArrayList<Integer>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		difficulties.add(difficulty);
		if (!hasEnoughAdvancements(difficulties, indices, size))
		{
			Bukkit.getLogger().log(Level.WARNING, "There are not enough advancements present to fill this card [abort]");
			return false;
		}

		ahaDifficulties.ensureCapacity(indices.size());
		for (int i : indices)
		{
			ahaDifficulties.add(holders.get(i));
		}
		
	
		Collections.shuffle(ahaDifficulties);
		
		getManager(id).getRoot().getDisplay().setCoordinates(-1, (float)((int)(size/2))); 
		
		for (int y = 0; y < size; y++ )
		{
			for (int x = 0; x < size; x++)
			{
				if (x == 0)
				{
					ahaDifficulties.get(y * size + x).makeAdvancement(id, getManager(id).getRoot(), x, y);

				}
				else
				{
					ahaDifficulties.get(y * size + x).makeAdvancement(id, holders.get(y * size + x - 1).getAdvancement(id), x, y);
				}
			}
			
		}
		for (AdvancementHolder holder : holders)
		{
			if (isAdvancementInNamespace(holder, id))
			{
				getManager(id).addAdvancement(holder.getAdvancement(id));
			}
		}
		
		getManager(id).addBingos();
		
		return true;
	}

	@Override
	public void removeManager(String id)
	{
		if (!holders.isEmpty())
		{
			for (AdvancementHolder holder : holders)
			{
				if (isAdvancementInNamespace(holder, id))
				{
					if (holder instanceof AdvancementHolderConsumables)
					{
						((AdvancementHolderConsumables)holder).clear();
					}
					getManager(id).removeAdvancement(holder.getAdvancement(id));
					holder.removeAdvancement(id);
				}
			}
		}
		getManager(id).removeRoot();
		getManager(id).removeAllPlayers();
		getManager(id).removeRemainingAdvancements();
		managerList.remove(id);
	}
	
	@Override
	public AdvancementManagerInstance getManager(String id) {
		return this.managerList.get(id);
	}
	
	public ArrayList<AdvancementHolder> getHolders(EventType event)
	{
		ArrayList<AdvancementHolder> temp = new ArrayList<AdvancementHolder>();
		for (AdvancementHolder a : holders)
		{
			if (a.getEventType() == event)
			{
				temp.add(a);
			}
		}
		return temp;
	}
	
	private boolean isAdvancementInNamespace(AdvancementHolder holder, String id)
	{
		return holder.getAdvancement(id) != null;
	}	
	
	@Override
	public Set<String> getNamespaces() {
		return managerList.keySet();
	}
	
	public boolean hasEnoughAdvancements(ArrayList<Integer> difficulties, ArrayList<Integer> indices,int size)
	{
		boolean hasZero = false;
		if (difficulties.contains(0))
		{
			hasZero = true;
			indices.ensureCapacity(holders.size());
		}
		else
		{
			indices.ensureCapacity(size * size);
		}
		
		
		int cAdvancementsPresent = 0;
		for (AdvancementHolder ah : holders)
		{
			if (difficulties.contains(ah.getDifficluty()) || hasZero)
			{
				cAdvancementsPresent++;
				indices.add(holders.indexOf(ah));
			}
		}
		return cAdvancementsPresent >= size;
	}
	
	@SafeVarargs
	private static Map<Material, Integer> generateMap(Pair<Material, Integer>... materials)
	{
		Map<Material, Integer> temp = new HashMap<Material, Integer>();
		for (Pair<Material, Integer> p  : materials)
		{
			temp.put(p.first, p.second);
			p.clear();
			p = null;
		}
		return temp;
	}
	
	private void addInInventoryAdvancement(int difficulty, String key, Material icon, String title, String description, Map<Material, Integer> materials)
	{
		holders.add(new AdvancementHolderInInventory(difficulty, key, icon, title, description,EventType.IN_INVENTORY, materials));
	}
	
	private void addhasConsumedAdvancement(int difficulty, String key, Material icon, String title, String description, Material... consumables)
	{
		holders.add(new AdvancementHolderConsumables(difficulty, key, icon, title, description,EventType.HAS_CONSUMED,consumables));
	}
	
	private void addPlacedBlockAdvancement(int difficulty, String key, Material icon, String title, String description, Pair<Material, Location> block)
	{
		holders.add(new AdvancementHolderPlaceBlock(difficulty, key, icon, title, description, EventType.BLOCK_PLACED, block));
	}
	
	private void addKillsEntityAdvancement(int difficulty, String key, Material icon, String title, String description, EntityType entity)
	{
		holders.add(new AdvancementHolderKillEntity(difficulty, key, icon, title, description, EventType.KILLED_ENTITY, entity));
	}
	
	private void addPlayerCraftAdvancement(int difficulty, String key, Material icon, String title, String description, Material material)
	{
		holders.add(new AdvancementHolderCraft(difficulty, key, icon, title, description, material));
	}
	
	private void addPlayerDeathAdvancement(int difficulty, String key, Material icon, String title, String description, EntityType entityType)
	{
		holders.add(new AdvancementHolderDeathBy(difficulty, key, icon, title, description, entityType));
	}
	

	
	private void initAdvancements()
	{
		addInInventoryAdvancement(-1, "diamondblock", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block",  generateMap(Pair.of(Material.DIAMOND_BLOCK, 1)));
		addInInventoryAdvancement(-1, "bookshelf", Material.BOOKSHELF, "Booked!", "Obtain 1 Bookshelf",  generateMap(Pair.of(Material.BOOKSHELF,1)));
		addInInventoryAdvancement(-1, "enchantmenttable", Material.ENCHANTING_TABLE, "Time for some magic :O", "Obtain 1 Enchantment Table",  generateMap(Pair.of(Material.ENCHANTING_TABLE, 1)));
		addInInventoryAdvancement(-1, "endcrystal", Material.END_CRYSTAL, "End Crystal EZ CLAP", "Obtain 1 End Crystal",  generateMap(Pair.of(Material.END_CRYSTAL, 1)));
		addInInventoryAdvancement(-1, "emeraldblock", Material.EMERALD_BLOCK, "For the Villager :)", "Obtain 1 Emerald Block",  generateMap(Pair.of(Material.EMERALD_BLOCK, 1)));
		addInInventoryAdvancement(-1, "brick", Material.BRICK, "Just like Legos", "Obtain 64 bricks",  generateMap(Pair.of(Material.BRICK, 64)));
		addInInventoryAdvancement(-1, "glisteringmelonslice", Material.GLISTERING_MELON_SLICE, "Watermelone", "Obtain 1 Glistering Melon Slice",  generateMap(Pair.of(Material.GLISTERING_MELON_SLICE, 1)));
		addInInventoryAdvancement(-1, "seapickle", Material.SEA_PICKLE, "I'M PICKLE RICK!!!", "Obtain 32 sea pickles",  generateMap(Pair.of(Material.SEA_PICKLE,32)));
		addInInventoryAdvancement(-1, "cookie", Material.COOKIE, "Just get 1 Cookie :)", "Obtain 1 Cookie",  generateMap(Pair.of(Material.COOKIE, 1)));
		addInInventoryAdvancement(-1, "phantommembrane", Material.PHANTOM_MEMBRANE, "Flycatcher", "Obtain 1 Phantom Membrane", generateMap(Pair.of(Material.PHANTOM_MEMBRANE, 1)));
		addInInventoryAdvancement(-1, "bucketfish", Material.TROPICAL_FISH_BUCKET, "Finding Nemo", "Obtain 1 tropical Fish in a Bucket", generateMap(Pair.of(Material.TROPICAL_FISH_BUCKET, 1)));
		addInInventoryAdvancement(-1, "saddle", Material.SADDLE, "Saddle up!", "Obtain a saddle", generateMap(Pair.of(Material.SADDLE, 1)));
		addInInventoryAdvancement(-1, "undyingtotem", Material.TOTEM_OF_UNDYING, "Shiny totem", "Obtain a Totem of Undying", generateMap(Pair.of(Material.TOTEM_OF_UNDYING, 1)));
		addInInventoryAdvancement(-1, "witherskull", Material.BOOK, "", "", generateMap(Pair.of(Material.BOOK, 1)));
		addInInventoryAdvancement(0, "cobblestone", Material.COBBLESTONE, "Cobble cobble", "Obtain 32 Cobblestone", generateMap(Pair.of(Material.COBBLESTONE, 32)));
		addInInventoryAdvancement(0, "ironchestplate", Material.POTION, "Suit up!", "Obtain 1 Iron Chestplate", generateMap(Pair.of(Material.POTION, 1)));
		addInInventoryAdvancement(0, "dirt", Material.DIRT, "Just dirt", "Obtain 32 Dirt", generateMap(Pair.of(Material.DIRT, 32)));
		addInInventoryAdvancement(0, "stonetools", Material.STONE_HOE, "..and that includes the Hoe", "Obtain every Stone tool", generateMap(Pair.of(Material.STONE_SWORD, 1),Pair.of(Material.STONE_PICKAXE, 1),Pair.of(Material.STONE_AXE, 1),Pair.of(Material.STONE_PICKAXE, 1),Pair.of(Material.STONE_HOE, 1)));
		addInInventoryAdvancement(0, "", Material.BOOK, "", "", generateMap(Pair.of(Material.BOOK, 1)));
		
		addhasConsumedAdvancement(-1, "goldenparty", Material.GOLDEN_APPLE, "tastes better plated in gold", "eat 1 golden apple", Material.GOLDEN_APPLE);
		addPlacedBlockAdvancement(-1, "haybale", Material.HAY_BLOCK, "Hay, what's up?", "Place a haybale on y=256", Pair.of(Material.HAY_BLOCK, new Location(null, 0, 255, 0)));
		addKillsEntityAdvancement(-1, "enderman", Material.ENDERMAN_SPAWN_EGG, "The Ender Ender!", "Kill an Enderman", EntityType.ENDERMAN);
		addPlayerDeathAdvancement(-1, "enderkill", Material.ENDERMITE_SPAWN_EGG, "Revenge of the ENDER", "Get killed by an Enderman", EntityType.ENDERMAN);
		addPlayerCraftAdvancement(-1, "beacon", Material.NETHER_STAR, "Ultimate Flex", "Craft a Beacon", Material.BEACON);
	}
}
