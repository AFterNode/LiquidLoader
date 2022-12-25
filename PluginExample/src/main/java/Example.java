import cn.afternode.liquidloader.Plugin;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;

public class Example extends Plugin {
    @Override
    public void onLoad() {
        System.out.println("1");
    }

    @Override
    public void registerModules(ModuleManager mm) {
        mm.registerModules(ExampleModule.class);
    }

    @Override
    public void registerCommands(CommandManager cm) {
        cm.registerCommand(new ExampleCommand());
    }
}
