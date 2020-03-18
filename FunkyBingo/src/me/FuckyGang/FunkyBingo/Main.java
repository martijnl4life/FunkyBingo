package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	private final int size = 5;
	
    @Override
    public void onEnable() 
    {
    	AdvancementManager manager = new AdvancementManager(this);
    	BingoCard card = new BingoCard(manager.getSelection(0, size), manager.getRoot(), manager.getUninitialised(), size);
    }
    
    @Override
    public void onDisable() 
    {
        
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        //hello
        
        if (label.equals("hello"))
        {
            sender.sendMessage("Hey bitch :)");
            return true;
        }
        
        return false;
    }
}