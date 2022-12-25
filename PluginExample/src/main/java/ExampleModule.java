import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(name = "ExampleModule", description = "Example", category = ModuleCategory.MISC)
public class ExampleModule extends Module {
    private final IntegerValue exampleValue = new IntegerValue("ExampleValue", 1, 0, 10);
}
