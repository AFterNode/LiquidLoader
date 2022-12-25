package cn.afternode.liquidloader;

import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;

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
}
