package com.khillynn;

import org.bukkit.*;
import org.bukkit.block.Block;
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

public class IcyFlurry extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
        final int radius = 8;
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("IcyFlurry is Enabled! =D");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        BukkitScheduler secScheduler = Bukkit.getServer().getScheduler();
        final BukkitScheduler thirdScheduler = Bukkit.getServer().getScheduler();

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
                    Location createLocLeft = new Location(getServer().getWorld("Ice_Game_Map"), xCreate - 1, 26, zCreate);
                    Location createLocUp = new Location(getServer().getWorld("Ice_Game_Map"), xCreate, 26, zCreate - 1);
                    Location createLocDiag = new Location(getServer().getWorld("Ice_Game_Map"), xCreate - 1, 26, zCreate - 1);
                    Location secCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), secXCreate, 26, secZCreate);
                    Location secCreateLocLeft = new Location(getServer().getWorld("Ice_Game_Map"), secXCreate - 1, 26, secZCreate);
                    Location secCreateLocUp = new Location(getServer().getWorld("Ice_Game_Map"), secXCreate, 26, secZCreate - 1);
                    Location secCreateLocDiag = new Location(getServer().getWorld("Ice_Game_Map"), secXCreate - 1, 26, secZCreate - 1);
                    Location thirdCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), thirdXCreate, 26, thirdZCreate);
                    Location thirdCreateLocLeft = new Location(getServer().getWorld("Ice_Game_Map"), thirdXCreate - 1, 26, thirdZCreate);
                    Location thirdCreateLocUp = new Location(getServer().getWorld("Ice_Game_Map"), thirdXCreate, 26, thirdZCreate - 1);
                    Location thirdCreateLocDiag = new Location(getServer().getWorld("Ice_Game_Map"), thirdXCreate - 1, 26, thirdZCreate - 1);
                    Location fourthCreateLoc = new Location(getServer().getWorld("Ice_Game_Map"), fourthXCreate, 26, fourthZCreate);
                    Location fourthCreateLocLeft = new Location(getServer().getWorld("Ice_Game_Map"), fourthXCreate - 1, 26, fourthZCreate);
                    Location fourthCreateLocUp = new Location(getServer().getWorld("Ice_Game_Map"), fourthXCreate, 26, fourthZCreate - 1);
                    Location fourthCreateLocDiag = new Location(getServer().getWorld("Ice_Game_Map"), fourthXCreate - 1, 26, fourthZCreate - 1);

                    final Block origBlockOne = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLoc);
                    final Block origBlockOneLeft = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLocLeft);
                    final Block origBlockOneUp = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLocUp);
                    final Block origBlockOneDiag = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(createLocDiag);
                    final Block origBlockTwo = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLoc);
                    final Block origBlockTwoLeft = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLocLeft);
                    final Block origBlockTwoUp = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLocUp);
                    final Block origBlockTwoDiag = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(secCreateLocDiag);
                    final Block origBlockThree = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLoc);
                    final Block origBlockThreeLeft = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLocLeft);
                    final Block origBlockThreeUp = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLocUp);
                    final Block origBlockThreeDiag = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(thirdCreateLocDiag);
                    final Block origBlockFour = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLoc);
                    final Block origBlockFourLeft = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLocLeft);
                    final Block origBlockFourUp = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLocUp);
                    final Block origBlockFourDiag = Bukkit.getServer().getWorld("Ice_Game_Map").getBlockAt(fourthCreateLocDiag);

                    final Material origTypeOne = origBlockOne.getType();
                    final Material origTypeOneLeft = origBlockOneLeft.getType();
                    final Material origTypeOneUp = origBlockOneUp.getType();
                    final Material origTypeOneDiag = origBlockOneDiag.getType();
                    final Material origTypeTwo = origBlockTwo.getType();
                    final Material origTypeTwoLeft = origBlockTwoLeft.getType();
                    final Material origTypeTwoUp = origBlockTwoUp.getType();
                    final Material origTypeTwoDiag = origBlockTwoDiag.getType();
                    final Material origTypeThree = origBlockThree.getType();
                    final Material origTypeThreeLeft = origBlockThreeLeft.getType();
                    final Material origTypeThreeUp = origBlockThreeUp.getType();
                    final Material origTypeThreeDiag = origBlockThreeDiag.getType();
                    final Material origTypeFour = origBlockFour.getType();
                    final Material origTypeFourLeft = origBlockFourLeft.getType();
                    final Material origTypeFourUp = origBlockFourUp.getType();
                    final Material origTypeFourDiag = origBlockFourDiag.getType();

                    //creates the ice cover
                    origBlockOne.setType(Material.PACKED_ICE);
                    origBlockOneLeft.setType(Material.PACKED_ICE);
                    origBlockOneUp.setType(Material.PACKED_ICE);
                    origBlockOneDiag.setType(Material.PACKED_ICE);
                    origBlockTwo.setType(Material.PACKED_ICE);
                    origBlockTwoLeft.setType(Material.PACKED_ICE);
                    origBlockTwoUp.setType(Material.PACKED_ICE);
                    origBlockTwoDiag.setType(Material.PACKED_ICE);
                    origBlockThree.setType(Material.PACKED_ICE);
                    origBlockThreeLeft.setType(Material.PACKED_ICE);
                    origBlockThreeUp.setType(Material.PACKED_ICE);
                    origBlockThreeDiag.setType(Material.PACKED_ICE);
                    origBlockFour.setType(Material.PACKED_ICE);
                    origBlockFourLeft.setType(Material.PACKED_ICE);
                    origBlockFourUp.setType(Material.PACKED_ICE);
                    origBlockFourDiag.setType(Material.PACKED_ICE);

                    //fifteen seconds after the ice cover is formed, the blocks go back to what they used to be
                    thirdScheduler.runTaskLater(IcyFlurry.this, new Runnable() {
                        public void run(){
                            origBlockOne.setType(origTypeOne);
                            origBlockOneLeft.setType(origTypeOneLeft);
                            origBlockOneUp.setType(origTypeOneUp);
                            origBlockOneDiag.setType(origTypeOneDiag);
                            origBlockTwo.setType(origTypeTwo);
                            origBlockTwoLeft.setType(origTypeTwoLeft);
                            origBlockTwoUp.setType(origTypeTwoUp);
                            origBlockTwoDiag.setType(origTypeTwoDiag);
                            origBlockThree.setType(origTypeThree);
                            origBlockThreeLeft.setType(origTypeThreeLeft);
                            origBlockThreeUp.setType(origTypeThreeUp);
                            origBlockThreeDiag.setType(origTypeThreeDiag);
                            origBlockFour.setType(origTypeFour);
                            origBlockFourLeft.setType(origTypeFourLeft);
                            origBlockFourUp.setType(origTypeFourUp);
                            origBlockFourDiag.setType(origTypeFourDiag);
                        }
                    }, 300L);
                }

                //stop music and drop hellHail
                else if (tenSeconds >= 7 && (tenSeconds %2 != 0)) {
                    Bukkit.getServer().getWorld("Ice_Game_Map").playEffect(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Effect.RECORD_PLAY, 0);
                    Bukkit.getServer().getWorld("Ice_Game_Map").playSound(Bukkit.getServer().getWorld("Ice_Game_Map").getSpawnLocation(), Sound.AMBIENCE_RAIN, 1, 2);

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
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 48){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "4");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 49){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "3");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 50){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "2");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 51){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "Don't Die.");
                    Bukkit.getWorld("Ice_Game_Map").playSound(loc, Sound.CLICK, 10, 1);
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
