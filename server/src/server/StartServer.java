package server;

import server.commands.*;
import server.manager.*;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import common.manager.Outputer;


/**
 * Main server class. Creates all server instances.
 *
 * @author Pozdnyakov Egor.
 */
public class StartServer {
    public static final int PORT = 5432;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = "LABA";
    private static final int MAX_CLIENTS = 1000;
    private static String databaseUsername = "s224994";
    private static String databaseHost;
    private static String databasePassword;
    private static String databaseAddress;
    private static int port;

    /*public static void main(String[] args) {
        FileManager collectionFileManager = new FileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new RemoveGreaterCommand(collectionManager),
                new HistoryCommand(),
                new CountLessThanOscarsCountCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ServerExitCommand(),
                new ClearCommand(collectionManager)
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }*/

    public static void main(String[] args) {
        //if (!initialize(args)) return;
        port = 5555;
        databaseHost = "pg";
        databasePassword = "6pXgNa";
        databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/studs";
        DatabaseHandler databaseHandler = new DatabaseHandler(databaseAddress, databaseUsername, databasePassword);
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseHandler);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseHandler, databaseUserManager);
        CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager, databaseCollectionManager),
                new UpdateCommand(collectionManager, databaseCollectionManager),
                new RemoveByIdCommand(collectionManager, databaseCollectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new HistoryCommand(),
                new ServerExitCommand(),
                new ClearCommand(collectionManager, databaseCollectionManager),
                new LoginCommand(databaseUserManager),
                new RegisterCommand(databaseUserManager)

        );
        Server server = new Server(port, MAX_CLIENTS, commandManager);
        server.run();
        databaseHandler.closeConnection();
    }

    /**
     * Controls initialization.
     */
    /*private static boolean initialize(String[] args) {
        try {
            if (args.length != 3) throw new WrongAmountOfElementsException();
            port = Integer.parseInt(args[0]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            databaseHost = args[1];
            databasePassword = args[2];
            databaseAddress = "jdbc:postgresql://" + databaseHost + ":1821/postgres";
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(StartServer.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Использование: 'java -jar " + jarName + " <port> <db_host> <db_password>'");
        } catch (NumberFormatException exception) {
            Outputer.printerror("Порт должен быть представлен числом!");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Порт не может быть отрицательным!");
        }
        return false;
    }*/
}