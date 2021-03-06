package server.manager;

import common.manager.Outputer;
import server.StartServer;

import java.sql.*;

/**
 * A class for handle database.
 */
public class DatabaseHandler {
    // Table names
    public static final String MOVIE_TABLE = "movie";
    public static final String USER_TABLE = "my_user";
    public static final String COORDINATES_TABLE = "coordinates";
    public static final String OPERATOR_TABLE = "operator";
    // MOVIE_TABLE column names
    public static final String MOVIE_TABLE_ID_COLUMN = "id";
    public static final String MOVIE_TABLE_NAME_COLUMN = "name";
    public static final String MOVIE_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String MOVIE_TABLE_OSCARS_COLUMN = "oscars";
    public static final String MOVIE_TABLE_GENRES_COLUMN = "genres";
    public static final String MOVIE_TABLE_USA_BOX_OFFICE_COLUMN = "usa_box_office";
    public static final String MOVIE_TABLE_MPAA_RATING_COLUMN = "mpaa_rating";
    public static final String MOVIE_TABLE_OPERATOR_ID_COLUMN = "operator_id";
    public static final String MOVIE_TABLE_USER_ID_COLUMN = "user_id";
    // USER_TABLE column names
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";
    // COORDINATES_TABLE column names
    public static final String COORDINATES_TABLE_ID_COLUMN = "id";
    public static final String COORDINATES_TABLE_MOVIE_ID_COLUMN = "movie_id";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";
    // OPERATOR_TABLE column names
    public static final String OPERATOR_TABLE_ID_COLUMN = "id";
    public static final String OPERATOR_TABLE_NAME_COLUMN = "name";
    public static final String OPERATOR_TABLE_HEIGHT_COLUMN = "height";
    public static final String OPERATOR_TABLE_EYE_COLOR_COLUMN = "eye_color";
    public static final String OPERATOR_TABLE_HAIR_COLOR_COLUMN = "hair_color";
    public static final String OPERATOR_TABLE_NATIONALITY_COLUMN = "nationality";

    private final String JDBC_DRIVER = "org.postgresql.Driver";

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DatabaseHandler(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        connectToDataBase();
    }

    /**
     * A class for connect to database.
     */
    private void connectToDataBase() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
            Outputer.println("???????????????????? ?? ?????????? ???????????? ??????????????????????.");
        } catch (SQLException exception) {
            Outputer.printerror("?????????????????? ???????????? ?????? ?????????????????????? ?? ???????? ????????????!");
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("?????????????? ???????????????????? ?????????? ???????????? ???? ????????????!");
        }
    }

    /**
     * @param sqlStatement SQL statement to be prepared.
     * @param generateKeys Is keys needed to be generated.
     * @return Prepared statement.
     * @throws SQLException When there's exception inside.
     */
    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            return preparedStatement;
        } catch (SQLException exception) {
            throw new SQLException(exception);
        }
    }

    /**
     * Close prepared statement.
     *
     * @param sqlStatement SQL statement to be closed.
     */
    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
        } catch (SQLException exception) {
        }
    }

    /**
     * Close connection to database.
     */
    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            Outputer.println("???????????????????? ?? ?????????? ???????????? ??????????????????.");
        } catch (SQLException exception) {
            Outputer.printerror("?????????????????? ???????????? ?????? ?????????????? ???????????????????? ?? ?????????? ????????????!");
        }
    }

    /**
     * Set commit mode of database.
     */
    public void setCommitMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
        }
    }

    /**
     * Set normal mode of database.
     */
    public void setNormalMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
        }
    }

    /**
     * Commit database status.
     */
    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException exception) {
        }
    }

    /**
     * Roll back database status.
     */
    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException exception) {
        }
    }

    /**
     * Set save point of database.
     */
    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException exception) {
        }
    }
}