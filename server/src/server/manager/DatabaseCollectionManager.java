package server.manager;

import common.data.*;
import common.exceptions.DatabaseHandlingException;
import common.interaction.User;
import common.interaction.MovieRaw;
import common.manager.Outputer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 * Operates the database collection itself.
 */
public class DatabaseCollectionManager {
    // MOVIE_TABLE
    private final String SELECT_ALL_MOVIES = "SELECT * FROM " + DatabaseHandler.MOVIE_TABLE;
    private final String SELECT_MOVIE_BY_ID = SELECT_ALL_MOVIES + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_MOVIE_BY_ID_AND_USER_ID = SELECT_MOVIE_BY_ID + " AND " +
            DatabaseHandler.MOVIE_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_MOVIE = "INSERT INTO " +
            DatabaseHandler.MOVIE_TABLE + " (" +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_OSCARS_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_GENRES_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_USA_BOX_OFFICE_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_MPAA_RATING_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_OPERATOR_ID_COLUMN + ", " +
            DatabaseHandler.MOVIE_TABLE_USER_ID_COLUMN + ") VALUES (nextval('id'), ?, ?, ?, ?," +
            "?, ?, ?, ?)";
    private final String DELETE_MOVIE_BY_ID = "DELETE FROM " + DatabaseHandler.MOVIE_TABLE +
            " WHERE " + DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_NAME_BY_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.MOVIE_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_OSCARS_BY_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.MOVIE_TABLE_OSCARS_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_GENRE_BY_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.MOVIE_TABLE_GENRES_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_USA_BOX_OFFICE_BY_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.MOVIE_TABLE_USA_BOX_OFFICE_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_MPAA_RATING_BY_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.MOVIE_TABLE_MPAA_RATING_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.MOVIE_TABLE_ID_COLUMN + " = ?";
    // COORDINATES_TABLE
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseHandler.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_MOVIE_ID = SELECT_ALL_COORDINATES +
            " WHERE " + DatabaseHandler.COORDINATES_TABLE_MOVIE_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseHandler.COORDINATES_TABLE + " (" +
            DatabaseHandler.COORDINATES_TABLE_ID_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_MOVIE_ID_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + ") VALUES (nextval('coordinates_id'), ?, ?, ?)";
    private final String UPDATE_COORDINATES_BY_MOVIE_ID = "UPDATE " + DatabaseHandler.MOVIE_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.COORDINATES_TABLE_MOVIE_ID_COLUMN + " = ?";
    //OPERATOR_TABLE
    private final String SELECT_ALL_OPERATORS = "SELECT * FROM " + DatabaseHandler.OPERATOR_TABLE;
    private final String SELECT_OPERATOR_BY_ID = SELECT_ALL_OPERATORS +
            " WHERE " + DatabaseHandler.OPERATOR_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_OPERATOR = "INSERT INTO " +
            DatabaseHandler.OPERATOR_TABLE + " (" +
            DatabaseHandler.OPERATOR_TABLE_ID_COLUMN + ", " +
            DatabaseHandler.OPERATOR_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.OPERATOR_TABLE_HEIGHT_COLUMN + ", " +
            DatabaseHandler.OPERATOR_TABLE_EYE_COLOR_COLUMN + ", " +
            DatabaseHandler.OPERATOR_TABLE_HAIR_COLOR_COLUMN + ", " +
            DatabaseHandler.OPERATOR_TABLE_NATIONALITY_COLUMN + ") VALUES (nextval('operator_id'), ?, ?, ?, ?, ?)";
    private final String UPDATE_OPERATOR_BY_ID = "UPDATE " + DatabaseHandler.OPERATOR_TABLE + " SET " +
            DatabaseHandler.OPERATOR_TABLE_NAME_COLUMN + " = ?, " +
            DatabaseHandler.OPERATOR_TABLE_HEIGHT_COLUMN + " = ?" +
            DatabaseHandler.OPERATOR_TABLE_EYE_COLOR_COLUMN + " = ?" +
            DatabaseHandler.OPERATOR_TABLE_HAIR_COLOR_COLUMN + " = ?" +
            DatabaseHandler.OPERATOR_TABLE_NATIONALITY_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.OPERATOR_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_OPERATOR_BY_ID = "DELETE FROM " + DatabaseHandler.OPERATOR_TABLE +
            " WHERE " + DatabaseHandler.OPERATOR_TABLE_ID_COLUMN + " = ?";
    private DatabaseHandler databaseHandler;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseHandler databaseHandler, DatabaseUserManager databaseUserManager) {
        this.databaseHandler = databaseHandler;
        this.databaseUserManager = databaseUserManager;
    }

    /**
     * Create Movie.
     *
     * @param resultSet Result set parameters of Movie.
     * @return New Movie.
     * @throws SQLException When there's exception inside.
     */
    private Movie createMovie(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DatabaseHandler.MOVIE_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseHandler.MOVIE_TABLE_NAME_COLUMN);
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseHandler.MOVIE_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        Long oscars = resultSet.getLong(DatabaseHandler.MOVIE_TABLE_OSCARS_COLUMN);
        MovieGenre genre = MovieGenre.valueOf(resultSet.getString(DatabaseHandler.MOVIE_TABLE_GENRES_COLUMN));
        Long usaBoxOffice = resultSet.getLong(DatabaseHandler.MOVIE_TABLE_USA_BOX_OFFICE_COLUMN);
        MpaaRating mpaaRating = MpaaRating.valueOf(resultSet.getString(DatabaseHandler.MOVIE_TABLE_MPAA_RATING_COLUMN));
        Coordinates coordinates = getCoordinatesByMovieId(id);
        Person operator = getOperatorById(resultSet.getLong(DatabaseHandler.MOVIE_TABLE_OPERATOR_ID_COLUMN));
        User owner = databaseUserManager.getUserById(resultSet.getLong(DatabaseHandler.MOVIE_TABLE_USER_ID_COLUMN));
        return new Movie(
                id,
                name,
                coordinates,
                creationDate,
                oscars,
                usaBoxOffice,
                genre,
                mpaaRating,
                operator,
                owner
        );
    }

    /**
     * @return List of Movies.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public TreeMap<Long, Movie> getCollection() throws DatabaseHandlingException {
        TreeMap<Long, Movie> movieList = new TreeMap<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = databaseHandler.getPreparedStatement(SELECT_ALL_MOVIES, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                movieList.values().add(createMovie(resultSet));
            }
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return movieList;
    }

    /**
     * @param movieId Id of Movie.
     * @return Operator id.
     * @throws SQLException When there's exception inside.
     */
    private long getOperatorIdByMovieId(long movieId) throws SQLException {
        long operatorId;
        PreparedStatement preparedSelectMovieByIdStatement = null;
        try {
            preparedSelectMovieByIdStatement = databaseHandler.getPreparedStatement(SELECT_MOVIE_BY_ID, false);
            preparedSelectMovieByIdStatement.setLong(1, movieId);
            ResultSet resultSet = preparedSelectMovieByIdStatement.executeQuery();
            if (resultSet.next()) {
                operatorId = resultSet.getLong(DatabaseHandler.MOVIE_TABLE_OPERATOR_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectMovieByIdStatement);
        }
        return operatorId;
    }

    /**
     * @param movieId Id of Movie.
     * @return coordinates.
     * @throws SQLException When there's exception inside.
     */
    private Coordinates getCoordinatesByMovieId(long movieId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedSelectCoordinatesByMovieIdStatement = null;
        try {
            preparedSelectCoordinatesByMovieIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_COORDINATES_BY_MOVIE_ID, false);
            preparedSelectCoordinatesByMovieIdStatement.setLong(1, movieId);
            ResultSet resultSet = preparedSelectCoordinatesByMovieIdStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getDouble(DatabaseHandler.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseHandler.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectCoordinatesByMovieIdStatement);
        }
        return coordinates;
    }

    /**
     * @param operatorId Id of Operator.
     * @return Operator.
     * @throws SQLException When there's exception inside.
     */
    private Person getOperatorById(long operatorId) throws SQLException {
        Person operator;
        PreparedStatement preparedSelectOperatorByIdStatement = null;
        try {
            preparedSelectOperatorByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_OPERATOR_BY_ID, false);
            preparedSelectOperatorByIdStatement.setLong(1, operatorId);
            ResultSet resultSet = preparedSelectOperatorByIdStatement.executeQuery();
            if (resultSet.next()) {
                operator = new Person(
                        resultSet.getString(DatabaseHandler.OPERATOR_TABLE_NAME_COLUMN),
                        resultSet.getFloat(DatabaseHandler.OPERATOR_TABLE_HEIGHT_COLUMN),
                        Color.valueOf(resultSet.getString(DatabaseHandler.OPERATOR_TABLE_EYE_COLOR_COLUMN)),
                        Color.valueOf(resultSet.getString(DatabaseHandler.OPERATOR_TABLE_HAIR_COLOR_COLUMN)),
                        Country.valueOf(resultSet.getString(DatabaseHandler.OPERATOR_TABLE_NATIONALITY_COLUMN))
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectOperatorByIdStatement);
        }
        return operator;
    }

    /**
     * @param movieRaw Movie raw.
     * @param user      User.
     * @return Movie.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public Movie insertMovie(MovieRaw movieRaw, User user) throws DatabaseHandlingException {
        Movie movie;
        PreparedStatement preparedInsertMovieStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertOperatorStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertMovieStatement = databaseHandler.getPreparedStatement(INSERT_MOVIE, true);
            preparedInsertCoordinatesStatement = databaseHandler.getPreparedStatement(INSERT_COORDINATES, true);
            preparedInsertOperatorStatement = databaseHandler.getPreparedStatement(INSERT_OPERATOR, true);

            preparedInsertOperatorStatement.setString(1, movieRaw.getOperator().getName());
            preparedInsertOperatorStatement.setFloat(2, movieRaw.getOperator().getHeight());
            preparedInsertOperatorStatement.setString(3, movieRaw.getOperator().getEyeColor().toString());
            preparedInsertOperatorStatement.setString(4, movieRaw.getOperator().getHairColor().toString());
            preparedInsertOperatorStatement.setString(5, movieRaw.getOperator().getNationality().toString());
            if (preparedInsertOperatorStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedOperatorKeys = preparedInsertOperatorStatement.getGeneratedKeys();
            long operatorId;
            if (generatedOperatorKeys.next()) {
                operatorId = generatedOperatorKeys.getLong(1);
            } else throw new SQLException();

            preparedInsertMovieStatement.setString(1, movieRaw.getName());
            preparedInsertMovieStatement.setTimestamp(2, Timestamp.valueOf(creationTime));
            preparedInsertMovieStatement.setLong(3, movieRaw.getOscarCount());
            preparedInsertMovieStatement.setString(4, movieRaw.getGenre().toString());
            preparedInsertMovieStatement.setLong(5, movieRaw.getUsaBoxOffice());
            preparedInsertMovieStatement.setString(6, movieRaw.getMpaaRating().toString());
            preparedInsertMovieStatement.setLong(7, operatorId);
            preparedInsertMovieStatement.setLong(8, databaseUserManager.getUserIdByUsername(user));
            if (preparedInsertMovieStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedMovieKeys = preparedInsertMovieStatement.getGeneratedKeys();
            long movieId;
            if (generatedMovieKeys.next()) {
                movieId = generatedMovieKeys.getLong(1);
            } else throw new SQLException();

            preparedInsertCoordinatesStatement.setLong(1, movieId);
            preparedInsertCoordinatesStatement.setDouble(2, movieRaw.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setFloat(3, movieRaw.getCoordinates().getY());
            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) throw new SQLException();

            movie = new Movie(
                    movieId,
                    movieRaw.getName(),
                    movieRaw.getCoordinates(),
                    creationTime,
                    movieRaw.getOscarCount(),
                    movieRaw.getUsaBoxOffice(),
                    movieRaw.getGenre(),
                    movieRaw.getMpaaRating(),
                    movieRaw.getOperator(),
                    user
            );

            databaseHandler.commit();
            return movie;
        } catch (SQLException exception) {
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertMovieStatement);
            databaseHandler.closePreparedStatement(preparedInsertCoordinatesStatement);
            databaseHandler.closePreparedStatement(preparedInsertOperatorStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * @param movieRaw Movie raw.
     * @param movieId  Id of Movie.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void updateMovieById(long movieId, MovieRaw movieRaw) throws DatabaseHandlingException {
        PreparedStatement preparedUpdateMovieNameByIdStatement = null;
        PreparedStatement preparedUpdateMovieOscarsByIdStatement = null;
        PreparedStatement preparedUpdateMovieUsaBoxOfficeByIdStatement = null;
        PreparedStatement preparedUpdateMovieGenreByIdStatement = null;
        PreparedStatement preparedUpdateMovieMpaaRatingByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByMovieIdStatement = null;
        PreparedStatement preparedUpdateOperatorByIdStatement = null;
        LocalDateTime creationTime = LocalDateTime.now();
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            preparedUpdateMovieNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MOVIE_NAME_BY_ID, false);
            preparedUpdateMovieOscarsByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MOVIE_OSCARS_BY_ID, false);
            preparedUpdateMovieUsaBoxOfficeByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MOVIE_USA_BOX_OFFICE_BY_ID, false);
            preparedUpdateMovieGenreByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MOVIE_GENRE_BY_ID, false);
            preparedUpdateMovieMpaaRatingByIdStatement = databaseHandler.getPreparedStatement(UPDATE_MOVIE_MPAA_RATING_BY_ID, false);
            preparedUpdateCoordinatesByMovieIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATES_BY_MOVIE_ID, false);
            preparedUpdateOperatorByIdStatement = databaseHandler.getPreparedStatement(UPDATE_OPERATOR_BY_ID, false);

            if (movieRaw.getName() != null) {
                preparedUpdateMovieNameByIdStatement.setString(1, movieRaw.getName());
                preparedUpdateMovieNameByIdStatement.setLong(2, movieId);
                if (preparedUpdateMovieNameByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getCoordinates() != null) {
                preparedUpdateCoordinatesByMovieIdStatement.setDouble(1, movieRaw.getCoordinates().getX());
                preparedUpdateCoordinatesByMovieIdStatement.setFloat(2, movieRaw.getCoordinates().getY());
                preparedUpdateCoordinatesByMovieIdStatement.setLong(3, movieId);
                if (preparedUpdateCoordinatesByMovieIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getOscarCount() != -1) {
                preparedUpdateMovieOscarsByIdStatement.setDouble(1, movieRaw.getOscarCount());
                preparedUpdateMovieOscarsByIdStatement.setLong(2, movieId);
                if (preparedUpdateMovieOscarsByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getUsaBoxOffice() != null) {
                preparedUpdateMovieUsaBoxOfficeByIdStatement.setString(1, movieRaw.getUsaBoxOffice().toString());
                preparedUpdateMovieUsaBoxOfficeByIdStatement.setLong(2, movieId);
                if (preparedUpdateMovieUsaBoxOfficeByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getGenre() != null) {
                preparedUpdateMovieGenreByIdStatement.setString(1, movieRaw.getGenre().toString());
                preparedUpdateMovieGenreByIdStatement.setLong(2, movieId);
                if (preparedUpdateMovieGenreByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getMpaaRating() != null) {
                preparedUpdateMovieMpaaRatingByIdStatement.setString(1, movieRaw.getMpaaRating().toString());
                preparedUpdateMovieMpaaRatingByIdStatement.setLong(2, movieId);
                if (preparedUpdateMovieMpaaRatingByIdStatement.executeUpdate() == 0) throw new SQLException();
            }
            if (movieRaw.getOperator() != null) {
                preparedUpdateOperatorByIdStatement.setString(1, movieRaw.getOperator().getName());
                preparedUpdateOperatorByIdStatement.setFloat(2, movieRaw.getOperator().getHeight());
                preparedUpdateOperatorByIdStatement.setTimestamp(3, Timestamp.valueOf(creationTime));
                preparedUpdateOperatorByIdStatement.setLong(4, movieRaw.getOscarCount());
                preparedUpdateOperatorByIdStatement.setString(5, movieRaw.getUsaBoxOffice().toString());
                preparedUpdateOperatorByIdStatement.setString(6, movieRaw.getGenre().toString());
                preparedUpdateOperatorByIdStatement.setString(7, movieRaw.getMpaaRating().toString());
                preparedUpdateOperatorByIdStatement.setLong(8, getOperatorIdByMovieId(movieId));
                if (preparedUpdateOperatorByIdStatement.executeUpdate() == 0) throw new SQLException();
            }

            databaseHandler.commit();
        } catch (SQLException exception) {
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedUpdateMovieNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMovieOscarsByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMovieUsaBoxOfficeByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMovieGenreByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateMovieMpaaRatingByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesByMovieIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateOperatorByIdStatement);
            databaseHandler.setNormalMode();
        }
    }

    /**
     * Delete Movie by id.
     *
     * @param movieId Id of Movie.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void deleteMovieById(long movieId) throws DatabaseHandlingException {
        PreparedStatement preparedDeleteMovieByIdStatement = null;
        try {
            preparedDeleteMovieByIdStatement = databaseHandler.getPreparedStatement(DELETE_MOVIE_BY_ID, false);
            preparedDeleteMovieByIdStatement.setLong(1, getOperatorIdByMovieId(movieId));
            if (preparedDeleteMovieByIdStatement.executeUpdate() == 0) Outputer.println(3);
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedDeleteMovieByIdStatement);
        }
    }

    /**
     * Checks Movie user id.
     *
     * @param movieId Id of Movie.
     * @param user Owner of movie.
     * @throws DatabaseHandlingException When there's exception inside.
     * @return Is everything ok.
     */
    public boolean checkMovieUserId(long movieId, User user) throws DatabaseHandlingException {
        PreparedStatement preparedSelectMovieByIdAndUserIdStatement = null;
        try {
            preparedSelectMovieByIdAndUserIdStatement = databaseHandler.getPreparedStatement(SELECT_MOVIE_BY_ID_AND_USER_ID, false);
            preparedSelectMovieByIdAndUserIdStatement.setLong(1, movieId);
            preparedSelectMovieByIdAndUserIdStatement.setLong(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedSelectMovieByIdAndUserIdStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectMovieByIdAndUserIdStatement);
        }
    }

    /**
     * Clear the collection.
     *
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public void clearCollection() throws DatabaseHandlingException {
        TreeMap<Long, Movie> movieList = getCollection();
        for (Movie movie : movieList.values()) {
            deleteMovieById(movie.getId());
        }
    }
}