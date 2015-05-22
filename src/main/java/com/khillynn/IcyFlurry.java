package com.khillynn;

import org.bukkit.*;
import org.bukkit.entity.Damageable;
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
import org.bukkit.event.entity.PlayerDeathEvent;
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
        final int radius = 8;
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("IcyFlurry is Enabled! =D");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        BukkitScheduler secScheduler = Bukkit.getServer().getScheduler();

        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int tenSeconds = 0;
            Location firstCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1695, 26, -1011);
            Location secCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1695, 26, -1011);
            Location thirdCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1695, 26, -1011);
            Location fourthCorner = new Location(getServer().getWorld("Ice_Game_Map"), 1695, 26, -1011);

            public void run() {
                Location loc = Bukkit.getWorld("Ice_Game_Map").getSpawnLocation();
                tenSeconds++;

                if (tenSeconds == 3)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "30 seconds until the round begins");

                else if (tenSeconds >= 6 && (tenSeconds %2 == 0)) {
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, Material.RECORD_4);

                    //fix these, the 7x7 square isn't in the center of the arena
                    double xCreate = new Random().nextInt(7) + 1 + firstCorner.getX(),
                            zCreate = new Random().nextInt(7) + 1 + firstCorner.getZ(),
                            secXCreate = new Random().nextInt(7) + 1 + secCorner.getX(),
                            secZCreate = new Random().nextInt(7) + 1 + secCorner.getZ(),
                            thirdXCreate = new Random().nextInt(7) + 1 + thirdCorner.getX(),
                            thirdZCreate = new Random().nextInt(7) + 1 + thirdCorner.getZ(),
                            fourthXCreate = new Random().nextInt(7) + 1 + fourthCorner.getX(),
                            fourthZCreate = new Random().nextInt(7) + 1 + fourthCorner.getZ();
                    Location createLoc = new Location(getServer().getWorld("Ice_Game_Map"), xCreate, 26, zCreate);
                    Location secCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), secXCreate, 26, secZCreate);
                    Location thirdCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), thirdXCreate, 26, thirdZCreate);
                    Location fourthCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), fourthXCreate, 26, fourthZCreate);

                    /*Block origBlockOne = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc);
                    Block origBlockTwo = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLoc);
                    Block origBlockThree = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLoc);
                    Block origBlockFour = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLoc);*/

                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc).setType(Material.PACKED_ICE);
                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLoc).setType(Material.PACKED_ICE);
                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLoc).setType(Material.PACKED_ICE);
                    Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLoc).setType(Material.PACKED_ICE);

                    /*if (tenSeconds >= 8 && tenSeconds %2 == 0){
                        Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc).setType(origBlockOne.getType());
                        Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLoc).setType(origBlockTwo.getType());
                        Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLoc).setType(origBlockThree.getType());
                        Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLoc).setType(origBlockFour.getType());
                    }*/
                }

                //stop music and drop hellHail
                else if (tenSeconds >= 7 && (tenSeconds %2 != 0)) {
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, 0);
                    Bukkit.getServer().getWorld("Ice_Game_Map").playSound(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Sound.AMBIENCE_RAIN, 1, 2);

                    int i = 0;
                    double total = Math.pow((radius * 2) + 1, 3);
                    for (int theX = (0 - radius); theX <= radius; theX++) {
                        for (int theZ = (0 - radius); theZ <= radius; theZ++) {
                            Location l = new Location(loc.getWorld(), loc.getX() + theX, loc.getY() + 11, loc.getZ() + theZ);
                            Snowball hellHail = (Snowball) Bukkit.getWorld("Ice_Game_Map").spawnEntity(l, EntityType.SNOWBALL);
                            hellHail.setVelocity(new Vector(0, -1, 0));
                        }
                    }
               }
            }
        }, 0L, 200L); //runs once every ten seconds

        secScheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int seconds = 1;
            public void run() {
                seconds++;
                Location loc = Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation();

                if(seconds == 37)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "15 seconds until the round begins");

                else if (seconds == 47) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "5");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if (seconds == 48){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "4");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if (seconds == 49){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "3");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if (seconds == 50){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "2");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
                else if (seconds == 51){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "Don't Die.");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 1, 0);
                }
            }
        }, 0L, 20L); //runs once every second
    }

    //turns the player towards the White Horseman, Death
    @EventHandler
    public void onJoin (PlayerJoinEvent e){
        e.getPlayer().getLocation().setYaw(-89);
        e.getPlayer().getLocation().setPitch(-50);
    }

    @EventHandler
    public void playerDied (PlayerDeathEvent e){
        e.getDrops().clear();

        if (e.getEntity() != null){
            Player deadPlayer = e.getEntity();

            boolean deathFromHellHail = deadPlayer.getLastDamageCause() instanceof Snowball;
            boolean deathFromHypothermia = deadPlayer.getLastDamageCause() instanceof Damageable;

            if (deathFromHellHail)
                e.setDeathMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + e.getEntity().getName() + ChatColor.GRAY + " Was Killed By HellHail!");
            if (deathFromHypothermia)
                e.setDeathMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + e.getEntity().getName() + ChatColor.GRAY + " Was Killed By Hypothermia!");
        }
    }

    @EventHandler
    public void onRespawn (PlayerRespawnEvent e){
        e.getPlayer().hidePlayer(e.getPlayer());
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

    //first nums in this is the center of the arena
    /* X: 1698, 1706, 1690, 1695, 1701
       Y: 29
       Z: -1004, -1007, -1001, -1012, -996*/

    /* X: 1695, 1701, 1706, 1690,
       Y: 26
       Z: -1011, -995, -1006, -1000*/
}
