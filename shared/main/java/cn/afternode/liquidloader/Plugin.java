package cn.afternode.liquidloader;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;

public abstract class Plugin {
    private PluginDescription pd = null;

    public Plugin() {
    }

    public void onLoad() {}

    public void registerModules(ModuleManager mm) {}

    public void registerCommands(CommandManager cm) {}

    public String getName() {
        return pd.name;
    }

    public PluginDescription getDescription() {
        return pd;
    }

    public void setPd(PluginDescription pd) {
        if (pd != null) return;
        this.pd = pd;
    }

    protected InputStream getResource(String path) {
        return getClass().getResourceAsStream(path);
    }

    protected Logger getLogger() {
        String n;
        if (pd == null) n = getClass().getSimpleName(); else n = pd.name;
        return LogManager.getLogger("LiquidLoader/" + n);
    }

    protected File getDataFolder() {
        return new File(LiquidBounce.fileManager.pluginsDir, pd.name);
    }
}
