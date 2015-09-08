package com.khillynn;

import org.bukkit.*;
import org.bukkit.block.Block;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IcyFlurry extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
        final String world = "Ice_Game_Map";
        final int hellHailRadius = 9, coverRadius = 6;
        final ArrayList<Location> locations = new ArrayList<Location>();
        final ArrayList<Material> music = new ArrayList<Material>();
        music.add(Material.GREEN_RECORD);
        music.add(Material.RECORD_3);
        music.add(Material.RECORD_4);
        music.add(Material.RECORD_5);
        music.add(Material.RECORD_6);
        music.add(Material.RECORD_7);
        music.add(Material.RECORD_8);
        music.add(Material.RECORD_9);
        music.add(Material.RECORD_12);
        final List<Integer> randomXZs = new ArrayList<>();
        final List<Double> createXZs = new ArrayList<>();
        final Location loc = Bukkit.getWorld(world).getSpawnLocation();

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("IcyFlurry is Enabled! =D");

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        BukkitScheduler secScheduler = Bukkit.getServer().getScheduler();
        final BukkitScheduler thirdScheduler = Bukkit.getServer().getScheduler();

        for (int theX = (0 - coverRadius); theX <= coverRadius; theX++) {
            for (int theZ = (0 - coverRadius); theZ <= coverRadius; theZ++) {
                locations.add(new Location(loc.getWorld(), loc.getX() + theX, loc.getY() + 26, loc.getZ() + theZ));
            }
        }

        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            int tenSeconds = 0;

            public void run() {
                tenSeconds++;

                if (tenSeconds == 3)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "30 seconds until the round begins");

                else if (tenSeconds >= 6 && (tenSeconds %2 == 0)) {
                    int diskNum = new Random().nextInt(music.size());
                    Bukkit.getServer().getWorld(world).playEffect(Bukkit.getServer().getWorld(world).getSpawnLocation(), Effect.RECORD_PLAY, music.get(diskNum));

                    //this for-loop gives 2 random locations within the arena
                    for(int locLoop = 1; locLoop <= 2; locLoop++){
                        int randomX = new Random().nextInt(locations.size());
                        int randomZ = new Random().nextInt(locations.size());
                        double xCreate = locations.get(randomX).getX();
                        double zCreate = locations.get(randomZ).getZ();

                        if(locLoop == 1){
                            randomXZs.clear();
                            createXZs.clear();
                        }

                        randomXZs.add(randomX);
                        randomXZs.add(randomZ);
                        createXZs.add(xCreate);
                        createXZs.add(zCreate);
                    }

                    Location createLoc = new Location(getServer().getWorld(world), createXZs.get(0), 26, createXZs.get(1));
                    Location createLocLeft = new Location(getServer().getWorld(world), createXZs.get(0) - 1, 26, createXZs.get(1));
                    Location createLocUp = new Location(getServer().getWorld(world), createXZs.get(0), 26, createXZs.get(1) - 1);
                    Location createLocDiag = new Location(getServer().getWorld(world), createXZs.get(0) - 1, 26, createXZs.get(1) - 1);
                    Location secCreateLoc = new Location(getServer().getWorld(world), createXZs.get(2), 26, createXZs.get(3));
                    Location secCreateLocLeft = new Location(getServer().getWorld(world), createXZs.get(2) - 1, 26, createXZs.get(3));
                    Location secCreateLocUp = new Location(getServer().getWorld(world), createXZs.get(2), 26, createXZs.get(3) - 1);
                    Location secCreateLocDiag = new Location(getServer().getWorld(world), createXZs.get(2) - 1, 26, createXZs.get(3) - 1);
                    /*Location thirdCreateLoc = new Location(getServer().getWorld(world), createXZs.get(4), 26, createXZs.get(5));
                    Location thirdCreateLocLeft = new Location(getServer().getWorld(world), createXZs.get(4) - 1, 26, createXZs.get(5));
                    Location thirdCreateLocUp = new Location(getServer().getWorld(world), createXZs.get(4), 26, createXZs.get(5) - 1);
                    Location thirdCreateLocDiag = new Location(getServer().getWorld(world), createXZs.get(4) - 1, 26, createXZs.get(5) - 1);
                    Location fourthCreateLoc = new Location(getServer().getWorld(world), createXZs.get(6), 26, createXZs.get(7));
                    Location fourthCreateLocLeft = new Location(getServer().getWorld(world), createXZs.get(6) - 1, 26, createXZs.get(7));
                    Location fourthCreateLocUp = new Location(getServer().getWorld(world), createXZs.get(6), 26, createXZs.get(7) - 1);
                    Location fourthCreateLocDiag = new Location(getServer().getWorld(world), createXZs.get(6) - 1, 26, createXZs.get(7) - 1);*/

                    final Block origBlockOne = Bukkit.getServer().getWorld(world).getBlockAt(createLoc);
                    final Block origBlockOneLeft = Bukkit.getServer().getWorld(world).getBlockAt(createLocLeft);
                    final Block origBlockOneUp = Bukkit.getServer().getWorld(world).getBlockAt(createLocUp);
                    final Block origBlockOneDiag = Bukkit.getServer().getWorld(world).getBlockAt(createLocDiag);
                    final Block origBlockTwo = Bukkit.getServer().getWorld(world).getBlockAt(secCreateLoc);
                    final Block origBlockTwoLeft = Bukkit.getServer().getWorld(world).getBlockAt(secCreateLocLeft);
                    final Block origBlockTwoUp = Bukkit.getServer().getWorld(world).getBlockAt(secCreateLocUp);
                    final Block origBlockTwoDiag = Bukkit.getServer().getWorld(world).getBlockAt(secCreateLocDiag);
                    /*final Block origBlockThree = Bukkit.getServer().getWorld(world).getBlockAt(thirdCreateLoc);
                    final Block origBlockThreeLeft = Bukkit.getServer().getWorld(world).getBlockAt(thirdCreateLocLeft);
                    final Block origBlockThreeUp = Bukkit.getServer().getWorld(world).getBlockAt(thirdCreateLocUp);
                    final Block origBlockThreeDiag = Bukkit.getServer().getWorld(world).getBlockAt(thirdCreateLocDiag);
                    final Block origBlockFour = Bukkit.getServer().getWorld(world).getBlockAt(fourthCreateLoc);
                    final Block origBlockFourLeft = Bukkit.getServer().getWorld(world).getBlockAt(fourthCreateLocLeft);
                    final Block origBlockFourUp = Bukkit.getServer().getWorld(world).getBlockAt(fourthCreateLocUp);
                    final Block origBlockFourDiag = Bukkit.getServer().getWorld(world).getBlockAt(fourthCreateLocDiag);*/

                    final Material origTypeOne = origBlockOne.getType();
                    final Material origTypeOneLeft = origBlockOneLeft.getType();
                    final Material origTypeOneUp = origBlockOneUp.getType();
                    final Material origTypeOneDiag = origBlockOneDiag.getType();
                    final Material origTypeTwo = origBlockTwo.getType();
                    final Material origTypeTwoLeft = origBlockTwoLeft.getType();
                    final Material origTypeTwoUp = origBlockTwoUp.getType();
                    final Material origTypeTwoDiag = origBlockTwoDiag.getType();
                    /*final Material origTypeThree = origBlockThree.getType();
                    final Material origTypeThreeLeft = origBlockThreeLeft.getType();
                    final Material origTypeThreeUp = origBlockThreeUp.getType();
                    final Material origTypeThreeDiag = origBlockThreeDiag.getType();
                    final Material origTypeFour = origBlockFour.getType();
                    final Material origTypeFourLeft = origBlockFourLeft.getType();
                    final Material origTypeFourUp = origBlockFourUp.getType();
                    final Material origTypeFourDiag = origBlockFourDiag.getType();*/

                    //creates the ice cover
                    origBlockOne.setType(Material.PACKED_ICE);
                    origBlockOneLeft.setType(Material.PACKED_ICE);
                    origBlockOneUp.setType(Material.PACKED_ICE);
                    origBlockOneDiag.setType(Material.PACKED_ICE);
                    origBlockTwo.setType(Material.PACKED_ICE);
                    origBlockTwoLeft.setType(Material.PACKED_ICE);
                    origBlockTwoUp.setType(Material.PACKED_ICE);
                    origBlockTwoDiag.setType(Material.PACKED_ICE);
                    /*origBlockThree.setType(Material.PACKED_ICE);
                    origBlockThreeLeft.setType(Material.PACKED_ICE);
                    origBlockThreeUp.setType(Material.PACKED_ICE);
                    origBlockThreeDiag.setType(Material.PACKED_ICE);
                    origBlockFour.setType(Material.PACKED_ICE);
                    origBlockFourLeft.setType(Material.PACKED_ICE);
                    origBlockFourUp.setType(Material.PACKED_ICE);
                    origBlockFourDiag.setType(Material.PACKED_ICE);*/

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
                            /*origBlockThree.setType(origTypeThree);
                            origBlockThreeLeft.setType(origTypeThreeLeft);
                            origBlockThreeUp.setType(origTypeThreeUp);
                            origBlockThreeDiag.setType(origTypeThreeDiag);
                            origBlockFour.setType(origTypeFour);
                            origBlockFourLeft.setType(origTypeFourLeft);
                            origBlockFourUp.setType(origTypeFourUp);
                            origBlockFourDiag.setType(origTypeFourDiag);*/
                        }
                    }, 300L); //fifteen seconds after the ice cover is formed, the blocks go back to what they used to be
                }

                //stop music and drop hellHail
                else if (tenSeconds >= 7 && (tenSeconds %2 != 0)) {
                    Bukkit.getServer().getWorld(world).playEffect(Bukkit.getServer().getWorld(world).getSpawnLocation(), Effect.RECORD_PLAY, 0);
                    Bukkit.getServer().getWorld(world).playSound(Bukkit.getServer().getWorld(world).getSpawnLocation(), Sound.AMBIENCE_RAIN, 1, 2);

                    for (int theX = (0 - hellHailRadius); theX <= hellHailRadius; theX++) {
                        for (int theZ = (0 - hellHailRadius); theZ <= hellHailRadius; theZ++) {
                            Location l = new Location(loc.getWorld(), loc.getX() + theX, loc.getY() + 11, loc.getZ() + theZ);
                            Snowball hellHail = (Snowball) Bukkit.getWorld(world).spawnEntity(l, EntityType.SNOWBALL);
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
                Location loc = Bukkit.getServer().getWorld(world).getSpawnLocation();

                if(seconds == 37)
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "15 seconds until the round begins");

                else if (seconds == 47) {
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "5");
                    Bukkit.getWorld(world).playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 48){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "4");
                    Bukkit.getWorld(world).playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 49){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "3");
                    Bukkit.getWorld(world).playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 50){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "2");
                    Bukkit.getWorld(world).playSound(loc, Sound.CLICK, 10, 1);
                }
                else if (seconds == 51){
                    Bukkit.broadcastMessage("[" + ChatColor.GOLD + "Server" + ChatColor.WHITE + "]: " + ChatColor.YELLOW + "Don't Die.");
                    Bukkit.getWorld(world).playSound(loc, Sound.CLICK, 10, 1);
                }
            }
        }, 0L, 20L); //runs once every second
    }

    //clears any possible items dropped items and displays custom death messages
    @EventHandler
    public void playerDied (PlayerDeathEvent e){
        e.getDrops().clear();
        if (e.getDeathMessage().contains("died"))
            e.setDeathMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + e.getEntity().getName() + ChatColor.GRAY + " was pummeled to death by HellHail!");
        if (e.getDeathMessage().contains("was slain by"))
            e.setDeathMessage("[" + ChatColor.GOLD + "Server:" + ChatColor.WHITE + "] " + ChatColor.RED + e.getEntity().getName() + ChatColor.GRAY + " died from hypothermia!");
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
            e.getPlayer().damage(6.0, e.getPlayer());
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
}
