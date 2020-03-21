package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	private ManagerInterface manager;
	private CommandManager cmdManager;
	private EventManager eventManager;
	
    @Override
    public void onEnable() 
    {
    	this.manager = new Manager();
        this.cmdManager = new CommandManager(this.manager);
        this.eventManager = new EventManager(this.manager);
        getServer().getPluginManager().registerEvents(eventManager, this);
        getCommand("bc").setTabCompleter(new BingoTabCompleter());
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