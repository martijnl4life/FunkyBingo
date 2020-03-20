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
        this.cmdManager = new CommandManager();
    }
    
    @Override
    public void onDisable() 
    {
        
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
    	return cmdManager.onCommand(sender, cmd, label, args);
    }
}