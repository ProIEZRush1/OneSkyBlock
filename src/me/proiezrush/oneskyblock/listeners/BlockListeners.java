package me.proiezrush.oneskyblock.listeners;

import me.proiezrush.oneskyblock.Main;
import me.proiezrush.oneskyblock.island.Island;
import me.proiezrush.oneskyblock.island.IslandPlayer;
import me.proiezrush.oneskyblock.listeners.breaker.Breaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockListeners implements Listener {

    private final Main m;
    public BlockListeners(Main m) {
        this.m = m;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack item = p.getItemInHand();
        IslandPlayer player = m.getPlayer(p);
        if (player.isInIsland()) {
            if (b.equals(m.getIsland(player).getOb().clone().subtract(new Vector(0D, 1D, 0D)).getBlock())) {
                Collection<ItemStack> x = b.getDrops();
                b.setType(Material.AIR);
                if (b.getType().equals(Material.CHEST) && p.hasPermission("oneskyblock.luckychest")) {
                    luckyChest(p, m);
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> Breaker.breakBlock(p, b, x, m), 1L);
                item.setDurability((short) (item.getDurability() + 50));

                //Leveling system
                checkLevels(player);

                e.setCancelled(true);
            }
        }
    }

    private void luckyChest(Player p, Main m) {
        ConfigurationSection a = m.getBlocks().getConfig().getConfigurationSection("luckychest-contents");
        int random = m.getRandomNumberInRange(0, a.getKeys(false).size());
        List<String> luckyChest = m.getBlocks().getList("luckychest-contents." + random);
        for (String s : luckyChest) {
            String[] value = s.split(":");
            String material = value[0];
            int data = Integer.parseInt(value[1]);
            int amount = Integer.parseInt(value[2]);
            ItemStack item = new ItemStack(Material.valueOf(material), amount, (short) data);
            p.getInventory().addItem(item);
        }
    }

    private void checkLevels(IslandPlayer player) {
        player.addBlock();
        List<String> levels = m.getC().getList("Levels");
        for (String level : levels) {
            if (player.getBlocksMined() == Integer.parseInt(level)) {
                player.addLevel();
            }
        }
    }
}
