
package mashup.game;

import java.util.Optional;

/**
 *
 * @author Elizabeth
 */
enum Command {

    MENU("menu"), SKIP("skip"), HINT("hint"), QUIT("quit");

    private final String commandString;
    private static final InputScrubber scrubber = new InputScrubber();
    private static final Command[] commands = Command.values();

    private Command(String commandString) {
        this.commandString = commandString;
    }

    public static Optional<Command> getCommand(String input) {
        input = scrubber.scrubText(input);
        for (Command command : commands) {
            if (command.commandString.equals(input)) {
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }
}
