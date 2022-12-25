import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;

public class ExampleCommand extends Command {
    public ExampleCommand() {
        super("examplecommand", "examplealias");
    }

    @Override
    public void execute(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s).append(" ");
        }
        ClientUtils.displayChatMessage(sb.toString());
    }
}
