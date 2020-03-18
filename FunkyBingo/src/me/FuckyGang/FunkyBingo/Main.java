package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	private CommandManager cmdManager;
	
    @Override
    public void onEnable() 
    {
    	cmdManager = new CommandManager(new AdvancementManager(this));
    }
    
    @Override
    public void onDisable() 
    {
        
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        return cmdManager.command(sender, cmd, label, args);
    }
}