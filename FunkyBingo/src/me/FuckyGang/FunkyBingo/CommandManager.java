package me.FuckyGang.FunkyBingo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager 
{	
	
	private final String[] queries = {"create", "reset"};
	
	public CommandManager()
	{
		
	}
	
	public boolean command(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("bc"))
		{
			
		}
		return false;
	}
}
