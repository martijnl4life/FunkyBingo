package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import eu.endercentral.crazy_advancements.Advancement;

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
		
	
		Collections.shuffle(this.holders);
		
		getManager(id).getRoot().getDisplay().setCoordinates(-1, (float)((int)(size/2))); 
		
		for (int y = 0; y < size; y++ )
		{
			for (int x = 0; x < size; x++)
			{
				if (x == 0)
				{
					holders.get(y * size + x).makeAdvancement(id, getManager(id).getRoot(), x, y);

				}
				else
				{
					holders.get(y * size + x).makeAdvancement(id, holders.get(y * size + x - 1).getAdvancement(id), x, y);
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
					getManager(id).removeAdvancement(holder.getAdvancement(id));
					holder.removeAdvancement(id);
				}
			}
		}
		getManager(id).removeRoot();
		getManager(id).removeAllPlayers();
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
		holders.add(new AdvancementHolderInInventory(0, key, icon, title, description,EventType.IN_INVENTORY, materials));
	}
	
	private void addhasConsumedAdvancement(int difficulty, String key, Material icon, String title, String description, Material... consumables)
	{
		holders.add(new AdvancementHolderConsumables(0, key, icon, title, description,EventType.HAS_CONSUMED,consumables));
	}
	

	
	private void initAdvancements()
	{
		addInInventoryAdvancement(0, "diamondblock", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block",  generateMap(Pair.of(Material.DIAMOND_BLOCK, 1)));
		addInInventoryAdvancement(0, "bookshelf", Material.BOOKSHELF, "Booked!", "Obtain 1 Bookshelf",  generateMap(Pair.of(Material.BOOKSHELF,1)));
		addInInventoryAdvancement(0, "enchantmenttable", Material.ENCHANTING_TABLE, "Time for some magic :O", "Obtain 1 Enchantment Table",  generateMap(Pair.of(Material.ENCHANTING_TABLE, 1)));
		addInInventoryAdvancement(0, "endcrystal", Material.END_CRYSTAL, "End Crystal EZ CLAP", "Obtain 1 End Crystal",  generateMap(Pair.of(Material.END_CRYSTAL, 1)));
		addInInventoryAdvancement(0, "emeraldblock", Material.EMERALD_BLOCK, "For the Villager :)", "Obtain 1 Emerald Block",  generateMap(Pair.of(Material.EMERALD_BLOCK, 1)));
		addInInventoryAdvancement(0, "brick", Material.BRICK, "Just like Legos", "Obtain 64 bricks",  generateMap(Pair.of(Material.BRICK, 64)));
		addInInventoryAdvancement(0, "glisteringmelonslice", Material.GLISTERING_MELON_SLICE, "Watermelone", "Obtain 1 Glistering Melon Slice",  generateMap(Pair.of(Material.GLISTERING_MELON_SLICE, 1)));
		addInInventoryAdvancement(0, "seapickle", Material.SEA_PICKLE, "I'M PICKLE RICK!!!", "Obtain 32 sea pickles",  generateMap(Pair.of(Material.SEA_PICKLE,32)));
		addInInventoryAdvancement(0, "cookie", Material.COOKIE, "Just get 1 Cookie :)", "Obtain 1 Cookie",  generateMap(Pair.of(Material.COOKIE, 1)));
		addhasConsumedAdvancement(0, "goldenparty", Material.GOLDEN_APPLE, "tastes better plated in gold", "eat 1 golden apple", Material.GOLDEN_APPLE);
	}


}
