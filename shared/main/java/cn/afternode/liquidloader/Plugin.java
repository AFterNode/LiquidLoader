package cn.afternode.liquidloader;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;

public abstract class Plugin {
    private PluginDescription pd = null;

    public Plugin() {
    }

    public void onLoad() {}

    /**
     * You must register your modules here
     */
    public void registerModules(ModuleManager mm) {}

    /**
     * You must register your commands here
     */
    public void registerCommands(CommandManager cm) {}

    public void registerHudElements() {}

    protected final void registerHudElement(Class<? extends Element> element) {
        HUD.addElement(element);
    }

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

    /***
     * Get the resource from your plugin jar
     */
    protected InputStream getResource(String path) {
        return getClass().getResourceAsStream(path);
    }

    protected Logger getLogger() {
        String n;
        if (pd == null) n = getClass().getSimpleName(); else n = pd.name;
        return LogManager.getLogger("LiquidLoader/" + n);
    }

    /**
     * Get the data folder at the {pluginDir}/{pluginName}
     */
    protected File getDataFolder() {
        return new File(LiquidBounce.fileManager.pluginsDir, pd.name);
    }
}
