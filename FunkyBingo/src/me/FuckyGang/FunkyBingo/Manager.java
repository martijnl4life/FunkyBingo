package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	
		Collections.shuffle(this.holders);
		
		if (getManager(id).getRoot() == null)
		{
			Bukkit.getLogger().log(Level.SEVERE, "this nigga is null lol");
		}
		
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
		return true;
	}
	

	@Override
	public void resetCard(String id)
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
	}
	
	@Override
	public AdvancementManagerInstance getManager(String id) {
		return this.managerList.get(id);
	}
	
	private boolean isAdvancementInNamespace(AdvancementHolder holder, String id)
	{
		return holder.getAdvancement(id) != null;
	}
	
	
	private void initAdvancements()
	{
		addBingoTile(0, "diamondblock0", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock1", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock2", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock3", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock4", Material.DIAMOND_BLOCK, "bitch me", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock5", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock6", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock7", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
		addBingoTile(0, "diamondblock8", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
	}
	
	private void addBingoTile(int difficulty, String key, Material icon, String title, String description, int criteria)
	{
		holders.add(new AdvancementHolder(0, key, icon, title, description, criteria));
	}

	@Override
	public Set<String> getNamespaces() {
		return managerList.keySet();
	}
}
