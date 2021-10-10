package server.commands;

import common.interaction.User;

/**
 * Interface for all commands.
 */
public interface Command {
    String getDescription();
    String getUsage();
    String getName();
    boolean execute(String commandStringArgument, Object commandObjectArgument, User user);
}
