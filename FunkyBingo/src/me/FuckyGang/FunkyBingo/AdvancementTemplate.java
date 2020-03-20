package me.FuckyGang.FunkyBingo;


import org.bukkit.Material;

public class AdvancementTemplate {
	private String id;
	private String title;
	private String description;
	private Material icon;
	private int amount;
	
	public AdvancementTemplate(String id, String title, String description, Material icon, int amount)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.amount = amount;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Material getIcon()
	{
		return icon;
	}
	
	public int getAmount()
	{
		return amount;
	}
}
