package cn.afternode.liquidloader.commands;

import cn.afternode.liquidloader.LiquidLoader;
import cn.afternode.liquidloader.Plugin;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

public class PluginCommand extends Command {
    public PluginCommand() {
        super("liquidloader-plugins", "llplugins");
    }

    @Override
    public void execute(@NotNull String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("LiquidLoader Plugins[§a").append(LiquidBounce.liquidLoader.getPlugins().length).append("§r]: \n");
        for (Plugin plugin : LiquidBounce.liquidLoader.getPlugins()) {
            sb.append(plugin.getName()).append(", ");
        }
        ClientUtils.displayChatMessage(sb.toString());
    }
}
