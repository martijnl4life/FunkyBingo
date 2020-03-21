package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;

public class AdvancementHolder
{
	private int difficulty;
	private String key;
	
	private Map<String, Advancement> advancementsContainer;
	private Material icon;
	private String title;
	private String description;
	private int criteria;
	private AdvancementVisibility visibility = AdvancementVisibility.ALWAYS;
	
	public AdvancementHolder(int difficulty, String key, Material icon, String title, String description, int criteria)
	{
		this.advancementsContainer = new HashMap<String, Advancement>();
		this.difficulty = difficulty;
		this.key = key;
		this.icon = icon;
		this.title = title;
		this.description = description;
		this.criteria = criteria;
	}
	
	public Advancement makeAdvancement(String namespace, Advancement parent, float x, float y)
	{
		Advancement advancement = new Advancement(parent, generateNameKey(namespace), new AdvancementDisplay(icon, title, description, AdvancementFrame.TASK, true, true, visibility));
		advancement.getDisplay().setCoordinates(x, y);
		advancement.setCriteria(criteria);
		this.advancementsContainer.put(namespace, advancement);
		
		return advancement;
	}
	
	private NameKey generateNameKey(String namespace)
	{
		return new NameKey(namespace, key);
	}
	
	public void removeAdvancement(String namespace)
	{
		if (advancementsContainer.containsKey(namespace))
		{
			advancementsContainer.remove(namespace);
		}
	}
	
	public Advancement getAdvancement(String namespace)
	{
		return advancementsContainer.get(namespace);
	}
	
	public int getDifficluty()
	{
		return this.difficulty;
	}

	
	public Material getIcon()
	{
		return this.icon;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getKey()
	{
		return key;
	}
}