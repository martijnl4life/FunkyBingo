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
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

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
					ahaDifficulties.get(y * size + x).makeAdvancement(id, ahaDifficulties.get(y * size + x - 1).getAdvancement(id), x, y);
				}
			}
			
		}
		for (AdvancementHolder holder : ahaDifficulties)
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
	
	private void addEntityMountAdvancement(int difficulty, String key, Material icon, String title, String description, EntityType entityType)
	{
		holders.add(new AdvancementHolderRide(difficulty, key, icon, title, description, entityType));
	}
	
	private void addEntityBreedAdvancement(int difficulty, String key, Material icon, String title, String description, EntityType entityType)
	{
		holders.add(new AdvancementHolderBreed(difficulty, key, icon, title, description, entityType));
	}
	
	private void addBrewsPotionAdvancement(int difficulty, String key, Material icon, String title, String description, PotionType potionType)
	{
		holders.add(new AdvancementHolderBrew(difficulty, key, icon, title, description, potionType));
	}
	
	private void initAdvancements()
	{
		addInInventoryAdvancement(-1, "diamondblock", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block",  generateMap(Pair.of(Material.DIAMOND_BLOCK, 1)));
		addInInventoryAdvancement(-1, "bookshelf", Material.BOOKSHELF, "Booked!", "Obtain 1 Bookshelf",  generateMap(Pair.of(Material.BOOKSHELF,1)));
		addInInventoryAdvancement(-1, "enchantmenttable", Material.ENCHANTING_TABLE, "Time for some magic :O", "Obtain 1 Enchantment Table",  generateMap(Pair.of(Material.ENCHANTING_TABLE, 1)));
		addInInventoryAdvancement(-1, "endcrystal", Material.END_CRYSTAL, "End Crystal EZ CLAP", "Obtain 1 End Crystal",  generateMap(Pair.of(Material.END_CRYSTAL, 1)));
		addInInventoryAdvancement(-1, "emeraldblock", Material.EMERALD_BLOCK, "For the Villager :)", "Obtain 1 Emerald Block",  generateMap(Pair.of(Material.EMERALD_BLOCK, 1)));
		addInInventoryAdvancement(-1, "brick", Material.BRICK, "Just like Legos", "Obtain 64 bricks",  generateMap(Pair.of(Material.BRICK, 64)));
		addInInventoryAdvancement(-1, "glisteringmelonslice", Material.GLISTERING_MELON_SLICE, "Golden watermelone", "Obtain 1 Glistering Melon Slice",  generateMap(Pair.of(Material.GLISTERING_MELON_SLICE, 1)));
		addInInventoryAdvancement(-1, "seapickle", Material.SEA_PICKLE, "I'M PICKLE RICK!!!", "Obtain 32 sea pickles",  generateMap(Pair.of(Material.SEA_PICKLE,32)));
		addInInventoryAdvancement(-1, "cookie", Material.COOKIE, "Just get 1 Cookie :)", "Obtain 1 Cookie",  generateMap(Pair.of(Material.COOKIE, 1)));
		addInInventoryAdvancement(-1, "phantommembrane", Material.PHANTOM_MEMBRANE, "Flycatcher", "Obtain 1 Phantom Membrane", generateMap(Pair.of(Material.PHANTOM_MEMBRANE, 1)));
		addInInventoryAdvancement(-1, "tropicalfishbucket", Material.TROPICAL_FISH_BUCKET, "Finding Nemo", "Obtain 1 tropical Fish in a Bucket", generateMap(Pair.of(Material.TROPICAL_FISH_BUCKET, 1)));
		addInInventoryAdvancement(-1, "saddle", Material.SADDLE, "Saddle up!", "Obtain a saddle", generateMap(Pair.of(Material.SADDLE, 1)));
		addInInventoryAdvancement(-1, "undyingtotem", Material.TOTEM_OF_UNDYING, "Shiny totem", "Obtain 1 Totem of Undying", generateMap(Pair.of(Material.TOTEM_OF_UNDYING, 1)));
		addInInventoryAdvancement(-1, "witherskull", Material.WITHER_SKELETON_SKULL, "RNGesus", "Obtain 1 Wither Skeleton Skull", generateMap(Pair.of(Material.WITHER_SKELETON_SKULL, 1)));
		addInInventoryAdvancement(-1, "cobblestone", Material.COBBLESTONE, "Cobble cobble", "Obtain 32 Cobblestone", generateMap(Pair.of(Material.COBBLESTONE, 32)));
		addInInventoryAdvancement(-1, "ironchestplate", Material.IRON_CHESTPLATE, "Suit up!", "Obtain 1 Iron Chestplate", generateMap(Pair.of(Material.IRON_CHESTPLATE, 1)));
		addInInventoryAdvancement(-1, "dirt", Material.DIRT, "Just dirt", "Obtain 32 Dirt", generateMap(Pair.of(Material.DIRT, 32)));
		addInInventoryAdvancement(-1, "stonetools", Material.STONE_HOE, "..and that includes the Hoe", "Obtain every Stone tool", generateMap(Pair.of(Material.STONE_SWORD, 1),Pair.of(Material.STONE_PICKAXE, 1),Pair.of(Material.STONE_AXE, 1),Pair.of(Material.STONE_PICKAXE, 1),Pair.of(Material.STONE_HOE, 1)));
		addInInventoryAdvancement(-1, "coal", Material.COAL, "Free coal", "Obtain 10 Coal", generateMap(Pair.of(Material.COAL, 10)));
		addInInventoryAdvancement(-1, "diamond", Material.DIAMOND, "Diamond :O", "Obtain 1 diamond", generateMap(Pair.of(Material.DIAMOND, 1)));
		addInInventoryAdvancement(-1, "lavabucket", Material.LAVA_BUCKET, "How does the Bucket not melt?", "Obtain 1 Bucket of lava", generateMap(Pair.of(Material.LAVA, 1)));
		addInInventoryAdvancement(-1, "enderpearl", Material.ENDER_PEARL, "11 more to go..", "Obtain 1 Ender Pearl", generateMap(Pair.of(Material.ENDER_PEARL, 1)));
		addInInventoryAdvancement(-1, "diamondarmor", Material.DIAMOND_CHESTPLATE, "Suit up!..but better", "Obtain a full set of Diamond Armor", generateMap(Pair.of(Material.DIAMOND_HELMET, 1), Pair.of(Material.DIAMOND_CHESTPLATE, 1), Pair.of(Material.DIAMOND_LEGGINGS, 1), Pair.of(Material.DIAMOND_BOOTS, 1)));
		addInInventoryAdvancement(-1, "darkoak", Material.DARK_OAK_LOG, "The bigboy trees", "Obtain 64 Dark Oak Logs", generateMap(Pair.of(Material.DARK_OAK_LOG, 64)));
		addInInventoryAdvancement(-1, "steak", Material.COOKED_BEEF, "The finest Beef", "Obtain 64 Cooked Beef", generateMap(Pair.of(Material.COOKED_BEEF, 64)));
		addInInventoryAdvancement(-1, "emerald", Material.EMERALD, "Usefull for trading", "Obtain 1 Emerald", generateMap(Pair.of(Material.EMERALD, 1)));
		addInInventoryAdvancement(-1, "torch", Material.TORCH, "Light it up", "Obtain 64 Torches", generateMap(Pair.of(Material.TORCH, 64)));
		addInInventoryAdvancement(-1, "diamondhoe", Material.DIAMOND_HOE, "Arguably the best Tool", "Obtain 1 Diamond Hoe", generateMap(Pair.of(Material.DIAMOND_HOE, 1)));
		addInInventoryAdvancement(-1, "shield", Material.SHIELD, "Better than armor TBH", "Obtain 1 Shield", generateMap(Pair.of(Material.SHIELD, 1)));
		addInInventoryAdvancement(-1, "allium", Material.ALLIUM, "Pick and choose", "Obtain 1 Allium Flower", generateMap(Pair.of(Material.ALLIUM, 1)));
		addInInventoryAdvancement(-1, "lilyofthevalley", Material.LILY_OF_THE_VALLEY, "Pick and choose", "Obtain 1 Allium", generateMap(Pair.of(Material.LILY_OF_THE_VALLEY, 1)));
		addInInventoryAdvancement(-1, "blueorchid", Material.BLUE_ORCHID, "Pick and choose", "Obtain 1 Blue Orchid", generateMap(Pair.of(Material.BLUE_ORCHID, 1)));
		addInInventoryAdvancement(-1, "sunflower", Material.SUNFLOWER, "Pick and choose", "Obtain 1 Sunflower", generateMap(Pair.of(Material.SUNFLOWER, 1)));
		addInInventoryAdvancement(-1, "pumpkinseeds", Material.PUMPKIN_SEEDS, "The seeds", "Obtain 1 Pumpkin Seed", generateMap(Pair.of(Material.PUMPKIN_SEEDS, 1)));
		addInInventoryAdvancement(-1, "melonseeds", Material.MELON_SEEDS, "The seed V2", "Obtain 1 Melon Seed", generateMap(Pair.of(Material.MELON_SEEDS, 1)));
		addInInventoryAdvancement(-1, "ghasttear", Material.GHAST_TEAR, "Make them cry", "Obtain 1 Ghast Tear", generateMap(Pair.of(Material.GHAST_TEAR, 1)));
		addInInventoryAdvancement(-1, "slimeball", Material.SLIME_BALL, "Slimey!", "Obtain 1 Slime Ball", generateMap(Pair.of(Material.SLIME_BALL, 1)));
		addInInventoryAdvancement(-1, "nautilusshell", Material.NAUTILUS_SHELL, "From his cold, wet hands", "Obtain 1 Nautilus Shell", generateMap(Pair.of(Material.NAUTILUS_SHELL, 1)));
		addInInventoryAdvancement(-1, "seaheart", Material.HEART_OF_THE_SEA, "Treasure!", "Obtain 1 Heart of the Sea", generateMap(Pair.of(Material.HEART_OF_THE_SEA, 1)));
		addInInventoryAdvancement(-1, "allfish", Material.PUFFERFISH, "Fishing time", "Obtain all fish (Cod, Salmon, Pufferfish)", generateMap(Pair.of(Material.PUFFERFISH, 1)));
		addInInventoryAdvancement(-1, "map", Material.FILLED_MAP, "Mapped out", "Obtain 1 map", generateMap(Pair.of(Material.FILLED_MAP, 1)));
		addInInventoryAdvancement(-1, "rabbitfoot", Material.RABBIT_FOOT, "Lucky you", "Obtain a Rabbit's Foot", generateMap(Pair.of(Material.RABBIT_FOOT, 1)));
		addInInventoryAdvancement(-1, "dragonbreath", Material.DRAGON_BREATH, "Smelly breath in a bottle", "Obtain 1 Bottle of Dragon's Breath", generateMap(Pair.of(Material.DRAGON_BREATH, 1)));
		addInInventoryAdvancement(-1, "blazerod", Material.BLAZE_ROD, "Kill the blaze", "Obtain 1 Blaze Rod", generateMap(Pair.of(Material.BLAZE_ROD, 1)));
		addInInventoryAdvancement(-1, "chainmail", Material.CHAINMAIL_CHESTPLATE, "In the chains", "Obtain a full set of Chainmail Armor", generateMap(Pair.of(Material.CHAINMAIL_HELMET, 1), Pair.of(Material.CHAINMAIL_CHESTPLATE, 1), Pair.of(Material.CHAINMAIL_LEGGINGS, 1), Pair.of(Material.CHAINMAIL_BOOTS, 1)));
		addInInventoryAdvancement(-1, "boneblock", Material.BONE_BLOCK, "The Bonezone", "Obtain a Bone Block", generateMap(Pair.of(Material.BONE_BLOCK, 1)));
		addInInventoryAdvancement(-1, "bread", Material.BREAD, "I Loaf U", "Obtain 1 baguette", generateMap(Pair.of(Material.BREAD, 1)));
		addInInventoryAdvancement(-1, "melonblock", Material.MELON, "Watermelone!", "Obtain 1 Melon", generateMap(Pair.of(Material.MELON, 1)));
		addInInventoryAdvancement(-1, "pufferfishbucket", Material.PUFFERFISH_BUCKET, "A toxic boi", "Obtain 1  Pufferfish in a Bucket", generateMap(Pair.of(Material.PUFFERFISH_BUCKET, 1)));

		addPlayerDeathAdvancement(-1, "enderkill", Material.ENDERMITE_SPAWN_EGG, "Revenge of the ENDER", "Get killed by an Enderman", EntityType.ENDERMAN);
		addPlayerDeathAdvancement(-1, "elderguardiankill", Material.ELDER_GUARDIAN_SPAWN_EGG, "Don't Disturb the Guardian", "Get killed by an Elder Guardian", EntityType.ELDER_GUARDIAN);
		addPlayerDeathAdvancement(-1, "witherkill", Material.WITHER_ROSE, "Withered Away", "Get killed by the Wither", EntityType.WITHER);
		
		addPlayerCraftAdvancement(-1, "beacon", Material.NETHER_STAR, "Ultimate Flex", "Craft a Beacon", Material.BEACON);
		addPlayerCraftAdvancement(-1, "pumpkinpie", Material.PUMPKIN_PIE, "Pie is better than Cake, fight me.", "Craft a Pumpkin Pie", Material.PUMPKIN_PIE);
		addPlayerCraftAdvancement(-1, "cake", Material.CAKE, "Cake is better than Pie, fight me.", "Craft a Cake", Material.CAKE);
		addPlayerCraftAdvancement(-1, "conduit", Material.CONDUIT, "I have obtained The Conduit", "Craft a Conduit", Material.CONDUIT);
		addPlayerCraftAdvancement(-1, "compass", Material.COMPASS, "For when you lost the Spawn", "Craft a Compass", Material.COMPASS);
		addPlayerCraftAdvancement(-1, "clock", Material.CLOCK, "For when you lost the Time", "Craft a Clock", Material.CLOCK);
		addPlayerCraftAdvancement(-1, "beetrootsoup", Material.BEETROOT_SOUP, "The bad soup", "Craft a Beetroot Soup", Material.BEETROOT_SOUP);
		addPlayerCraftAdvancement(-1, "mushroomstew", Material.MUSHROOM_STEW, "The good soup", "Craft a Mushroom Stew", Material.MUSHROOM_STEW);

		addhasConsumedAdvancement(-1, "goldenparty", Material.GOLDEN_APPLE, "tastes better plated in gold", "eat a golden apple", Material.GOLDEN_APPLE);
		addhasConsumedAdvancement(-1, "rabbitstew", Material.RABBIT_STEW, "Overcomplicated Eating", "Eat a RabbitStew", Material.CLOCK);
		addhasConsumedAdvancement(-1, "milk", Material.MILK_BUCKET, "Milky time!", "Drink a bucket of Milk", Material.MILK_BUCKET);

		addPlacedBlockAdvancement(-1, "haybale", Material.HAY_BLOCK, "Hay, what's up?", "Place a haybale at 0 255 0", Pair.of(Material.HAY_BLOCK, new Location(null, 0, 255, 0)));
		addKillsEntityAdvancement(-1, "enderman", Material.ENDERMAN_SPAWN_EGG, "The Ender Ender!", "Kill an Enderman", EntityType.ENDERMAN);
		
		addEntityBreedAdvancement(-1,"cows", Material.COW_SPAWN_EGG, "MOOOOOO", "Breed a cow", EntityType.COW);
		addEntityMountAdvancement(-1, "piggy", Material.CARROT_ON_A_STICK, "ideal transport", "Ride a Pig", EntityType.PIG);
		addBrewsPotionAdvancement(-1, "awkward", Material.SPLASH_POTION, "awkward encounter", "brew an awkward potion", PotionType.AWKWARD);
		//68 
	}
}
