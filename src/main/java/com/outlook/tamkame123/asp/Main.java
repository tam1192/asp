package com.outlook.tamkame123.asp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Runnable{
	protected FileConfiguration config = getConfig();
	Logger logger = getLogger();
	public void run() {
		int count = PlayerChecker.PlayerCheck();
		int maxCount = config.getInt("wait time[min]");
		if(count == maxCount) {
			logger.info("server shutdown");
			Bukkit.shutdown();
		} else {
			if(count != -1) {
				logger.warning("The server will stop in " + (maxCount-count) + " minutes.");
			}
			Bukkit.getScheduler().runTaskLater(this, this, 1200L);
		}
	}
    @Override
    public void onEnable() {
    	// Default configuration set
    	config.addDefault("wait time[min]", 10);
    	config.addDefault("isDisabled", false);
    	config.options().copyDefaults();
    	saveDefaultConfig();
    	// isDisabled set
    	PlayerChecker.isDisabled(config.getBoolean("isDisabled"));
    	// commands 
    	this.getCommand("asp").setExecutor(new Commands());
    	// next checks
    	Bukkit.getScheduler().runTaskLater(this, this, 1200L);
    }
}