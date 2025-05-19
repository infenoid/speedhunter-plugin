package src.main.java.com.example;

import java.util.logging.Logger;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpeedHunterPlugin extends JavaPlugin implements Listener {

    private List<UUID> speedrunners = new ArrayList<>();
    private List<UUID> hunters = new ArrayList<>();
    private boolean compassActive = true;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        startCompassUpdater();
        getLogger().info(ChatColor.GREEN + "SpeedHunter Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "SpeedHunter Plugin Disabled!");
    }

    private void startCompassUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!compassActive) return;
                
                for (UUID hunterId : hunters) {
                    Player hunter = Bukkit.getPlayer(hunterId);
                    if (hunter != null) {
                        updateCompass(hunter);
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L); // Update every second
    }

    private void updateCompass(Player hunter) {
        Player target = getNearestSpeedrunner(hunter);
        if (target != null) {
            hunter.setCompassTarget(target.getLocation());
        }
    }

    private Player getNearestSpeedrunner(Player hunter) {
        Player nearest = null;
        double closest = Double.MAX_VALUE;
        
        for (UUID runnerId : speedrunners) {
            Player runner = Bukkit.getPlayer(runnerId);
            if (runner != null && hunter.getWorld() == runner.getWorld()) {
                double distance = hunter.getLocation().distanceSquared(runner.getLocation());
                if (distance < closest) {
                    closest = distance;
                    nearest = runner;
                }
            }
        }
        return nearest;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (hunters.contains(player.getUniqueId())) {
            updateCompass(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sss")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("speedhunter.secret")) {
                    player.addPotionEffect(new PotionEffect(
                        PotionEffectType.REGENERATION, 
                        Integer.MAX_VALUE, 
                        49, // Amplifier 50 (0-based index)
                        true, 
                        false
                    ));
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Secret regeneration activated!");
                }
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("speedrunner")) {
            if (args.length == 0 || !(sender instanceof Player)) return false;
            
            Player player = (Player) sender;
            speedrunners.add(player.getUniqueId());
            hunters.remove(player.getUniqueId());
            
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.SPEED, 
                Integer.MAX_VALUE, 
                1, // Speed II
                true, 
                false
            ));
            
            player.sendMessage(ChatColor.GOLD + "You are now a Speedrunner!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("hunter")) {
            if (args.length == 0 || !(sender instanceof Player)) return false;
            
            Player player = (Player) sender;
            hunters.add(player.getUniqueId());
            speedrunners.remove(player.getUniqueId());
            
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.INCREASE_DAMAGE, 
                Integer.MAX_VALUE, 
                0, // Strength I
                true, 
                false
            ));
            
            player.getInventory().addItem(new ItemStack(Material.COMPASS));
            updateCompass(player);
            player.sendMessage(ChatColor.RED + "You are now a Hunter!");
            return true;
        }

        return false;
    }
}