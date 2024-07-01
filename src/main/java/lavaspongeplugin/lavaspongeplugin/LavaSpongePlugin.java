package lavaspongeplugin.lavaspongeplugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LavaSpongePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, 21783);
        this.getLogger().info("Thank you for using the LavaSpongePlugin plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        // Check if the block is a wet sponge
        if (block.getType() == Material.WET_SPONGE) {
            boolean lavaFound = false;
            // Check blocks in a 7-block radius for lava
            for (int dx = -7; dx <= 7; dx++) {
                for (int dy = -7; dy <= 7; dy++) {
                    for (int dz = -7; dz <= 7; dz++) {
                        if (dx * dx + dy * dy + dz * dz <= 49) { // Check within a spherical radius
                            Block neighbor = block.getRelative(dx, dy, dz);
                            if (neighbor.getType() == Material.LAVA) {
                                // Turn lava into air (simulating absorption)
                                neighbor.setType(Material.AIR);
                                lavaFound = true;
                            }
                        }
                    }
                }
            }

            // Change the wet sponge to a dry sponge if any lava was found
            if (lavaFound) {
                block.setType(Material.SPONGE);
            }
        }
    }
}
