package me.FuckyGang.FunkyBingo;

import org.bukkit.Location;
import org.bukkit.World;

public class Position 
{
	private boolean importantX = false;
	private boolean importantY = false;
	private boolean importantZ = false;
	private int x, y, z;
	
	private Location blockpos;
	
	public Position(World worldIn, String x, String y, String z)
	{
		
		if(areIntegers(x))
		{
			this.x = Integer.parseInt(x);
			this.importantX = true;
		}
		if(areIntegers(y))
		{
			this.x = Integer.parseInt(y);
			this.importantY = true;
		}
		if(areIntegers(z))
		{
			this.z = Integer.parseInt(z);
			this.importantZ = true;
		}
		
		this.blockpos = new Location(worldIn, this.x, this.y, this.z);
	}
	
	private boolean areIntegers(String... strings)
	{
		for (String s : strings)
		{
			if (s.isEmpty()) return false;
			for(int i = 0; i < s.length(); i++)
			{
				if(i == 0 && s.charAt(i) == '-')
				{
		            if(s.length() == 1) return false;
		            else continue;
		        }
				if(Character.digit(s.charAt(i),10) < 0 ) return false;
			}
		}
		return true;
	}
	
	public Location getBlockPos()
	{
		return this.blockpos;
	}

	public boolean isImportantX() {
		return importantX;
	}

	public boolean isImportantY() {
		return importantY;
	}

	public boolean isImportantZ() {
		return importantZ;
	}
}
