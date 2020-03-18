package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
    @Override
    public void onEnable() 
    {
    	AdvancementManager manager = new AdvancementManager(this);
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