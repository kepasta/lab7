package server.commands;

import common.data.*;
import common.exceptions.*;
import common.interaction.MovieRaw;
import common.interaction.User;
import server.manager.CollectionManager;
import server.manager.DatabaseCollectionManager;
import server.manager.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            Movie oldMovie = collectionManager.getById(id);
            if (oldMovie == null) throw new MovieNotFoundException();
            if (!oldMovie.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkMovieUserId(oldMovie.getId(), user)) throw new ManualDatabaseEditException();
            MovieRaw movieRaw = (MovieRaw) objectArgument;

            databaseCollectionManager.updateMovieById(id, movieRaw);

            String name = movieRaw.getName() == null ? oldMovie.getName() : movieRaw.getName();
            Coordinates coordinates = movieRaw.getCoordinates() == null ? oldMovie.getCoordinates() : movieRaw.getCoordinates();
            LocalDateTime creationDate = oldMovie.getCreationDate();
            Long oscars = movieRaw.getOscarCount() == -1 ? oldMovie.getOscarCount() : movieRaw.getOscarCount();
            Long usaBoxOffice = movieRaw.getUsaBoxOffice() == null ? oldMovie.getUsaBoxOffice() : movieRaw.getUsaBoxOffice();
            MovieGenre genre = movieRaw.getGenre() == null ? oldMovie.getGenre() : movieRaw.getGenre();
            MpaaRating mpaaRating = movieRaw.getMpaaRating() == null ? oldMovie.getMpaaRating() : movieRaw.getMpaaRating();
            Person operator = movieRaw.getOperator() == null ? oldMovie.getOperator() : movieRaw.getOperator();

            collectionManager.removeFromCollection(oldMovie);
            collectionManager.addToCollection(new Movie(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    oscars,
                    usaBoxOffice,
                    genre,
                    mpaaRating,
                    operator,
                    user
            ));
            ResponseOutputer.appendln("Солдат успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен положительным числом!");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.appenderror("Солдата с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("Недостаточно прав для выполнения данной команды!");
            ResponseOutputer.appendln("Принадлежащие другим пользователям объекты доступны только для чтения.");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("Произошло прямое изменение базы данных!");
            ResponseOutputer.appendln("Перезапустите клиент для избежания возможных ошибок.");
        }
        return false;
    }
}