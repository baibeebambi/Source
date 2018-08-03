package me.lolitsaods.DiscoArmor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener, Runnable {
    private final Set< UUID > discoPlayers = new HashSet<>();
    private final float speed = 0.025F;
    private float hue = 1F;

	@Override
    public void onEnable() {
    		getConfig().options().copyDefaults(true);
    		reloadConfig();
    		saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents( this, this );
        this.getServer().getScheduler().runTaskTimer( this, this, 0L, 1L );
		
	}

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) { if ( sender instanceof Player && sender.hasPermission( "disco.armor" ) ) {
        if ( sender instanceof Player && sender.hasPermission( "discoarmor.use" ) ) {
            Player player = (Player) sender;
            if ( this.discoPlayers.contains( player.getUniqueId() ) ) {
                this.discoPlayers.remove( player.getUniqueId() );
                player.sendMessage(getConfig().getString("prefix") + " §f" + getConfig().getString("disable"));
                ItemStack air = new ItemStack( Material.AIR );
                player.getInventory().setArmorContents( new ItemStack[] {
                        air, air, air, air
            } );
            player.updateInventory();
                } else {
                    this.discoPlayers.add( player.getUniqueId() );
                    player.sendMessage(getConfig().getString("prefix") + " §f" + getConfig().getString("enable"));
                }
            	}
		return false;
		}
	{
        }
	{
        }
	return false;
}

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        this.discoPlayers.remove( event.getPlayer().getUniqueId() );
        ItemStack air = new ItemStack( Material.AIR );
        Player player = event.getPlayer();
        player.getInventory().setArmorContents( new ItemStack[] {
                air, air, air, air
        } );
        player.updateInventory();
    }

    @Override
    public void run() {
        this.discoPlayers.forEach( uuid -> {
            Player player = Bukkit.getPlayer( uuid );
            if ( player != null ) {
                player.getInventory().setArmorContents( new ItemStack[] {
                        this.create( Material.LEATHER_BOOTS ),
                        this.create( Material.LEATHER_LEGGINGS ),
                        this.create( Material.LEATHER_CHESTPLATE ),
                        this.create( Material.LEATHER_HELMET )
                } );
                player.updateInventory();
            }
        } );
        this.hue += this.speed;
        if ( this.hue >= 1 ) {
            this.hue = 0;
        }
    }

    private ItemStack create( Material material ) {
        ItemStack itemStack = new ItemStack( material );
        LeatherArmorMeta leatherArmorMeta = ( LeatherArmorMeta ) itemStack.getItemMeta();
        java.awt.Color color = java.awt.Color.getHSBColor( this.hue, 1, 1 );
        leatherArmorMeta.setColor( Color.fromRGB( color.getRed(), color.getGreen(), color.getBlue() ) );
        itemStack.setItemMeta( leatherArmorMeta );
        return itemStack;
    		}		
	}
