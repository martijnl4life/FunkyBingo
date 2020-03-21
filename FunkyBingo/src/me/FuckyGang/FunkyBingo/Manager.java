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
		resetCard(id);
		
		this.managerList.put(id,new AdvancementManagerInstance(id) );
	
		Collections.shuffle(this.holders);

		for (int i = 0; i < holders.size(); i+=size )
		{
			for (int j = 0; j < size; j++)
			{
				if (j == 0)
				{
					holders.get(i).makeAdvancement(id, getManager(id).getRoot(), j, i/size);
				}
				else
				{
					holders.get(i + j).makeAdvancement(id, holders.get(i).getAdvancement(id), j, i/size);
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
		addBingoTile(0, "diamondblock4", Material.DIAMOND_BLOCK, "9 Diamonds Pogu", "Obtain 1 Diamond Block", 1);
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
