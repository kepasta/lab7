package server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import server.commands.Command;
import common.interaction.User;
import common.exceptions.HistoryIsEmptyException;

/**
 * Operates the commands.
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 8;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command addCommand;
    private Command updateCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command exitCommand;
    private Command executeScriptCommand;
    private Command historyCommand;
    private Command serverExitCommand;
    private Command loginCommand;
    private Command registerCommand;

    private ReadWriteLock historyLocker = new ReentrantReadWriteLock();
    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand, Command removeByIdCommand, Command exitCommand,
                          Command executeScriptCommand, Command historyCommand,
                          Command serverExitCommand, Command clearCommand, Command loginCommand, Command registerCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.historyCommand = historyCommand;
        this.serverExitCommand = serverExitCommand;
        this.clearCommand = clearCommand;
        this.loginCommand = loginCommand;
        this.registerCommand = registerCommand;

        // commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(exitCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(executeScriptCommand);
        commands.add(historyCommand);
        commands.add(helpCommand);
        commands.add(serverExitCommand);
        commands.add(clearCommand);
        commands.add(loginCommand);
        commands.add(registerCommand);
    }

    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     * @param user           User object.
     */
    public void addToHistory(String commandToStore, User user) {
        historyLocker.writeLock().lock();
        try {
            for (Command command : commands) {
                if (command.getName().equals(commandToStore)) {
                    for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                        commandHistory[i] = commandHistory[i - 1];
                    }
                    commandHistory[0] = commandToStore + " (" + user.getUsername() + ')';
                }
            }
        } finally {
            historyLocker.writeLock().unlock();
        }
    }


    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument, User user) {
        if (helpCommand.execute(stringArgument, objectArgument, user)) {
            for (Command command : commands) {
                ResponseOutputer.appendtable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return infoCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument, User user) {
        collectionLocker.readLock().lock();
        try {
            return showCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.readLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean add(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return addCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return updateCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return removeByIdCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument, User user) {
        collectionLocker.writeLock().lock();
        try {
            return clearCommand.execute(stringArgument, objectArgument, user);
        } finally {
            collectionLocker.writeLock().unlock();
        }
    }


    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument, User user) {
        return exitCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument, User user) {
        return executeScriptCommand.execute(stringArgument, objectArgument, user);
    }


    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument, User user) {
        if (historyCommand.execute(stringArgument, objectArgument, user)) {
            historyLocker.readLock().lock();
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();
                ResponseOutputer.appendln("Последние использованные команды:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("Ни одной команды еще не было использовано!");
            } finally {
                historyLocker.readLock().unlock();
            }
        }
        return false;
    }


    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument, User user) {
        return serverExitCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean login(String stringArgument, Object objectArgument, User user) {
        return loginCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean register(String stringArgument, Object objectArgument, User user) {
        return registerCommand.execute(stringArgument, objectArgument, user);
    }

    @Override
    public String toString() {
        return "CommandManager (вспомогательный класс для работы с командами)";
    }
}
