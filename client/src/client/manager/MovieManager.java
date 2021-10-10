package client.manager;

import java.util.NoSuchElementException;
import java.util.Scanner;

import common.data.MovieGenre;
import common.data.Location;
import common.data.Person;
import common.data.MpaaRating;
import common.data.Coordinates;
import common.data.Color;
import common.data.Country;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.manager.Outputer;
import client.StartClient;

/**
 * Asks a user movie's value.
 */
public class MovieManager {
    private final int MIN_Y = -454;
    private final long MIN_OSCARS = 1;
    private final long MIN_MARINES = 1;
    private final long MIN_HEIGHT = 0;

    private Scanner userScanner;
    private boolean fileMode;

    public MovieManager(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets movie asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets movie asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the movie's name.
     * @return Movie's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Outputer.println("Введите имя:");
                Outputer.print(StartClient.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks a user the movie's X coordinate.
     * @return Movie's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askX() throws IncorrectInputInScriptException {
        String strX;
        double x;
        while (true) {
            try {
                Outputer.println("Введите координату X:");
                Outputer.print(StartClient.PS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strX);
                x = Double.parseDouble(strX);
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the movie's Y coordinate.
     * @return Movie's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Float askY() throws IncorrectInputInScriptException {
        String strY;
        Float y;
        while (true) {
            try {
                Outputer.println("Введите координату Y > " + (MIN_Y+1) + ":");
                Outputer.print(StartClient.PS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strY);
                y = Float.parseFloat(strY);
                if (y < MIN_Y) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Координата Y не может быть ниже " + MIN_Y + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the movie's coordinates.
     * @return Movie's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        double x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the number of movie's oscars.
     * @return number of oscars.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Long askOscars() throws IncorrectInputInScriptException {
        String strOscars;
        Long oscars;
        while (true) {
            try {
                Outputer.println("Введите количество оскаров:");
                Outputer.print(StartClient.PS2);
                strOscars = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strOscars);
                oscars = Long.parseLong(strOscars);
                if (oscars < MIN_OSCARS) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Количество оскаров не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Количество оскаров должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Количество оскаров должно быть представлено целым числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return oscars;
    }

    public Long askUsaBoxOffice() throws IncorrectInputInScriptException {
        String strUsaBoxOffice;
        Long usaBoxOffice;
        while (true) {
            try {
                Outputer.println("Введите Сборы по США:");
                Outputer.print(StartClient.PS2);
                strUsaBoxOffice = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strUsaBoxOffice);
                usaBoxOffice = Long.parseLong(strUsaBoxOffice);
                if (usaBoxOffice < MIN_OSCARS) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Сборы не распознаны!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Сборы должны быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Сборы должны быть представлены целым числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return usaBoxOffice;
    }

    /**
     * Asks a user the movie's genre.
     * @return Movie's genre.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public MovieGenre askGenre() throws IncorrectInputInScriptException {
        String strGenre;
        MovieGenre genre;
        while (true) {
            try {
                Outputer.println("Список жанров - " + MovieGenre.nameList());
                Outputer.println("Введите жанр:");
                Outputer.print(StartClient.PS2);
                strGenre= userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strGenre);
                genre = MovieGenre.valueOf(strGenre.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Жанр не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Жанра нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return genre;
    }

    /**
     * Asks a user the movie's mpaa rating.
     * @return Movie's mpaa rating.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public MpaaRating askMpaaRating() throws IncorrectInputInScriptException {
        String strMpaaRating;
        MpaaRating mpaaRating;
        while (true) {
            try {
                Outputer.println("Список возрастных рейтингов - " + MpaaRating.nameList());
                Outputer.println("Введите возрастной рейтинг:");
                Outputer.print(StartClient.PS2);
                strMpaaRating = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strMpaaRating);
                mpaaRating = MpaaRating.valueOf(strMpaaRating.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Рейтинг не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Рейтинга нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return mpaaRating;
    }

    /**
     * Asks a user the operator's eye color.
     * @return Operator's eye color.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Color askEyeColor() throws IncorrectInputInScriptException {
        String strEyeColor;
        Color eyeColor;
        while (true) {
            try {
                Outputer.println("Список цветов глаз - " + Color.nameList());
                Outputer.println("Введите цвет глаз:");
                Outputer.print(StartClient.PS2);
                strEyeColor = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strEyeColor);
                eyeColor = Color.valueOf(strEyeColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Цвет не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Цвета нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return eyeColor;
    }

    public Color askHairColor() throws IncorrectInputInScriptException {
        String strHairColor;
        Color hairColor;
        while (true) {
            try {
                Outputer.println("Список цветов волос - " + Color.nameList());
                Outputer.println("Введите цвет волос:");
                Outputer.print(StartClient.PS2);
                strHairColor = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strHairColor);
                hairColor = Color.valueOf(strHairColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Цвет не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Цвета нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    /**
     * Asks a user the marine operator's name.
     * @return Operator's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askOperatorName() throws IncorrectInputInScriptException {
        String operatorName;
        while (true) {
            try {
                Outputer.println("Введите имя оператора:");
                Outputer.print(StartClient.PS2);
                operatorName = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(operatorName);
                if (operatorName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Имя оператора не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя оператора не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return operatorName;
    }

    /**
     * Asks a user the operator's height.
     * @return Operator's height.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public float askOperatorHeight() throws IncorrectInputInScriptException {
        String strOperatorHeight;
        float operatorHeight;
        while (true) {
            try {
                Outputer.println("Введите высоту оператора > " + (MIN_HEIGHT +1) + ":");
                Outputer.print(StartClient.PS2);
                strOperatorHeight = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strOperatorHeight);
                operatorHeight = Float.parseFloat(strOperatorHeight);
                if (operatorHeight < MIN_HEIGHT) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Рост оператора не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Рост оператора должен быть положительным и больше " + MIN_HEIGHT + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Outputer.printerror("Рост оператора должен быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return operatorHeight;
    }

    public Person askOperator() {
        String name = null;
        Float height = null;
        Color eyeColor = null;
        Color hairColor = null;
        Country nationality = null;
        try {
            name = askOperatorName();
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        }
        try {
            height = askOperatorHeight();
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        }
        try {
            eyeColor = askEyeColor();
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        }
        try {
            hairColor = askHairColor();
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        }
        try {
            nationality = askCountry();
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        }
        Location location = new Location(1., 1, 1,"123");
        Person operator = new Person(name, height, eyeColor, hairColor, nationality);
        return operator;

    }
    /**
     * Asks a user the operator's height.
     * @return Operator's height.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Country askCountry() throws IncorrectInputInScriptException {
        String strCountry;
        Country country;
        while (true) {
            try {
                Outputer.println("Список стран - " + Country.nameList());
                Outputer.println("Введите страну:");
                Outputer.print(StartClient.PS2);
                strCountry= userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strCountry);
                country = Country.valueOf(strCountry.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Жанр не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("Жанра нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return country;
    }
    /**
     * Asks a user a question.
     * @return Answer (true/false).
     * @param question A question.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                Outputer.print(StartClient.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }

    @Override
    public String toString() {
        return "Manager (вспомогательный класс для запросов пользователю)";
    }
}
