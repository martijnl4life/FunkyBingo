package me.FuckyGang.FunkyBingo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BingoTabCompleter implements TabCompleter 
{

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) 
	{
		if(sender instanceof Player)
		{ 
			List<String> list = new ArrayList<String>();
			if (cmd.getName().equalsIgnoreCase("bc") && args.length >= 2)
			{
				list.add("add");
				list.add("remove");
				if (args[0].equalsIgnoreCase("team"))
				{
					list.add("update");
				}
				return (List<String>)list;
			}
			else if (cmd.getName().equalsIgnoreCase("bc") && args.length >= 1)
			{
				list.add("card");
				list.add("player");
				list.add("team");
				return (List<String>)list;
			}
		}
		
		return null;
	}
	
}
