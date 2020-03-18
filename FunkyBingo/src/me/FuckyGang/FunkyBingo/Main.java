package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
    @Override
    public void onEnable() 
    {
        
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
            sender.sendMessage("Hey bitches :)");
            return true;
        }
        
        return false;
    }
}