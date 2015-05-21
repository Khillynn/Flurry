package com.khillynn;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Random;

public class IcyFlurry extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        final int dist = 8;
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("IcyFlurry is Enabled! =D");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int second = 0;
            Location firstCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1690, 26, -996);
            Location secCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1706, 26, -1012);

            public void run() {
                Location loc = Bukkit.getWorld("Ice_Game_Map").getSpawnLocation();
                second++;

                if (second == 30)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "30 seconds until the round begins");
                else if (second == 45)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "15 seconds");

                else if (second == 55) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.RED + "5");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                } else if (second == 56) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.RED + "4");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                } else if (second == 57) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.RED + "3");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                } else if (second == 58) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.RED + "2");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                } else if (second == 59) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.RED + "Don't Die.");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                } else if (second == 60 || second == 75) {
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, Material.RECORD_4);
                }


                //stop music and drop hellHail
                else if (second == 70 || second == 85) {
                    //fix these randoms, they are outside of the arena
                    double xCreate = (new Random().nextInt(10) + 1 + firstCorner.getX()),
                            zCreate = (new Random().nextInt(10) + 1 + firstCorner.getZ());
                    Location createLoc = new Location(getServer().getWorld("Ice_Game_Map"), xCreate, 26, zCreate);

                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, 0);
                    Bukkit.getServer().getWorld("Ice_Game_Map").playSound(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Sound.AMBIENCE_RAIN, 1, 2);
                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc).setType(Material.PACKED_ICE);

                    int i = 0;
                    double total = Math.pow((dist * 2) + 1, 3);
                    for (int theX = (0 - dist); theX <= dist; theX++) {
                        for (int theZ = (0 - dist); theZ <= dist; theZ++) {
                            Location l = new Location(loc.getWorld(), loc.getX() + theX, loc.getY() + 29, loc.getZ() + theZ);
                            Snowball hellHail = (Snowball) Bukkit.getWorld("Ice_Game_Map").spawnEntity(l, EntityType.SNOWBALL);
                            hellHail.setVelocity(new Vector(0, -1, 0));
                        }
                    }
                    //move this so it happens like 3 seconds after hellHail is sent
                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc).setType(Material.AIR);
                }
            }
        }, 0L, 20L);
    }

    //turns the player towards the White Horseman, Death
    @EventHandler
    public void onJoin (PlayerJoinEvent e){
        e.getPlayer().getLocation().setYaw(-89);
        e.getPlayer().getLocation().setPitch(-50);
    }

    @EventHandler
    public void onRespawn (PlayerRespawnEvent e){
        e.getPlayer().getLocation().setYaw(-89);
        e.getPlayer().getLocation().setPitch(-50);
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
    public void playerInWater (PlayerMoveEvent e) {
        Material maybeWater = e.getTo().getBlock().getType();
        Material maybeMoreWater = e.getFrom().getBlock().getType();

        if (maybeWater.equals(Material.WATER) || maybeWater.equals(Material.STATIONARY_WATER) || maybeMoreWater.equals(Material.WATER) || maybeMoreWater.equals(Material.STATIONARY_WATER)){
            e.getPlayer().damage(6.0);
        }
    }

    //prevents players from regaining health
    @EventHandler
    public void playerHPRegen (EntityRegainHealthEvent e){
        e.setCancelled(true);
    }

    //prevents players from becoming hungry
    @EventHandler
    public void playerHungerStop (FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

   //prevents players from picking up items
   @EventHandler
   public void itemPickUp (PlayerPickupItemEvent e){
       e.setCancelled(true);
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
