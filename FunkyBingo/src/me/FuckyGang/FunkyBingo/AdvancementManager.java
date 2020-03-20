package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import hu.trigary.advancementcreator.AdvancementFactory;
import hu.trigary.advancementcreator.Advancement;

public class AdvancementManager {
	
	private ArrayList<AdvancementTemplate> templates;
	private BingoCard card;
	
	public AdvancementManager(Plugin plugin)
	{
		templates = new ArrayList<AdvancementTemplate>();
	}
	
	public void addAdvancementsGetItem(String id, String title, String description, Material icon, int amount)
	{
		templates.add(new AdvancementTemplate(id,title,description,icon,amount));
	}
	
	public void fillAdvancements()
	{
		addAdvancementsGetItem("bingo/diamondblock","9 Diamonds Pogu","Obtain 1 Diamond Block",Material.DIAMOND_BLOCK,1 );
		addAdvancementsGetItem("bingo/bookshelf","Booked!","Obtain 1 Diamond Block",Material.BOOKSHELF,1);
		addAdvancementsGetItem("bingo/enchantmenttable","Time for some magic :O","Obtain 1 Diamond Block",Material.ENCHANTING_TABLE,1);
		addAdvancementsGetItem("bingo/endcrystal","A teary eye","Obtain 1 Diamond Block",Material.END_CRYSTAL,1);
		addAdvancementsGetItem("bingo/emeraldblock","For the Villagers :)","Obtain 1 Diamond Block",Material.EMERALD_BLOCK,1);
		addAdvancementsGetItem("bingo/brick","Just Like Legos","Obtain 1 Diamond Block",Material.BRICK,5);
		addAdvancementsGetItem("bingo/glisteringmelonslice","Watermelone","Obtain 1 Diamond Block",Material.GLISTERING_MELON_SLICE,1);
		addAdvancementsGetItem("bingo/seapickle","I'M PICKLE RICK!!!","Obtain 1 Diamond Block",Material.SEA_PICKLE,32);
		addAdvancementsGetItem("bingo/cookie","Just get 1 Cookie :)","Obtain 1 Cookie",Material.COOKIE,1);
	}

//		addAdvancement(0,factory.getItem("bingo/diamondblock", unInitialised, "9 Diamonds Pogu", "Obtain 1 Diamond Block", Material.DIAMOND_BLOCK));
//        addAdvancement(0,factory.getItem("bingo/bookshelf", unInitialised, "Booked!", "Obtain 1 Bookshelf", Material.BOOKSHELF));
//        addAdvancement(0,factory.getItem("bingo/enchantmenttable", unInitialised, "Time for some magic :O", "Obtain 1 Entchanting Table", Material.ENCHANTING_TABLE));
//        addAdvancement(0,factory.getItem("bingo/endcrystal", unInitialised, "A teary eye", "Obtain 1 End Crystal", Material.END_CRYSTAL));
//        addAdvancement(0,factory.getItem("bingo/emeraldblock", unInitialised, "For the Villagers :)", "Obtain 1 Emerald Block", Material.EMERALD_BLOCK));
//        addAdvancement(0,factory.getItem("bingo/brick", unInitialised, "Just Like Legos", "Obtain 5 Bricks", Material.BRICK, 5));
//        addAdvancement(0,factory.getItem("bingo/glisteringmelonslice", unInitialised, "Watermelone", "Obtain 1 Glistering Melon Slice", Material.GLISTERING_MELON_SLICE));
//        addAdvancement(0,factory.getItem("bingo/seapickle", unInitialised, "I'M PICKLE RICK!!!", "Obtain 32 sea pickles", Material.SEA_PICKLE, 32));
//        addAdvancement(0,factory.getItem("bingo/cookie", unInitialised, "Just get 1 Cookie :)", "Obtain 1 Cookie", Material.COOKIE));

}











