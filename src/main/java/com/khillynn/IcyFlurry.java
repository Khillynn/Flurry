package com.khillynn;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class IcyFlurry extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("IcyFlurry is Enabled! =D");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int second = 0;
            Location loc = Bukkit.getWorld("Ice_Game_Map").getSpawnLocation();
            public void run() {
                second++;

                if(second == 30)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.YELLOW + "30 seconds until the round begins");
                else if(second == 45)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.YELLOW + "15 seconds");

                else if(second == 55){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + "5");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if(second == 56){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + "4");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if(second == 57){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + "3");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if(second == 58){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + "2");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if(second == 59){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + "Don't Die.");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }

                else if(second == 60)
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, Material.getMaterial(2257));

                //stop music and drop death
                else if(second == 80) {
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, 0);
                }
            }
        }, 0L, 20L);
    }

    //stops weather from changing from the default in the world
    @EventHandler
    public void weatherChange (WeatherChangeEvent e){
        e.setCancelled(true);
    }

    //prevents blocks from being broken
    @EventHandler
    public void blockBreak (BlockBreakEvent e){
        e.setCancelled(true);
    }

    //prevents blocks from being destroyed
    @EventHandler
    public void breakingBlock (BlockDamageEvent e){
        e.setCancelled(true);
    }

    //prevents blocks from being placed
    @EventHandler
    public void placeBlock (BlockPlaceEvent e){
        e.setCancelled(true);
    }

    //players are hurt when they are in water
    @EventHandler
    public void playerInWater (PlayerMoveEvent e){
        Material maybeWater = e.getTo().getBlock().getType();

        if(maybeWater.equals(Material.WATER) || maybeWater.equals(Material.STATIONARY_WATER))
                e.getPlayer().damage(6.0);
    }

    //when the player is hit by a snowball they'll die
    @EventHandler
    public void playerHitBySnowball (EntityDamageByEntityEvent e){
        if((e.getDamager() instanceof Snowball) && (e.getEntity() instanceof Player))
            ((Player) e.getEntity()).setHealth(0.0);
    }



    /* X: 1698, 1706, 1690, 1695, 1701
       Y: 29
       Z: -1004, -1007, -1001, -1012, -996*/
}
