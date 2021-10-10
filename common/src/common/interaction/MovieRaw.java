package common.interaction;

import common.data.*;
import java.io.Serializable;

/**
 * Class for Movie value.
 */

public class MovieRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private Long oscarCount;
    private Long usaBoxOffice;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;

    public MovieRaw(String name, Coordinates coordinates, Long oscarCount,
                 Long usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person operator) {
        this.name = name;
        this.coordinates = coordinates;
        this.oscarCount = oscarCount;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
    }


    /**
     * @return Name of the movie.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coordinates of the movie.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }


    /**
     * @return Oscar count of the movie.
     */
    public Long getOscarCount() {
        return oscarCount;
    }

    /**
     * @return USA Box Office of the movie.
     */
    public Long getUsaBoxOffice() {
        return usaBoxOffice;
    }

    /**
     * @return Genre of the movie.
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * @return MPAA rating of the movie.
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * @return Chapter of the marine.
     */
    public Person getOperator() {
        return operator;
    }


    @Override
    public String toString() {
        String info = "";
        info += "\n Название: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Количество оскаров: " + oscarCount;
        info += "\n Сборы в США: " + usaBoxOffice;
        info += "\n Жанр: " + genre;
        info += "\n Рейтинг Американской киноассоциации " + mpaaRating;
        info += "\n Оператор: " + operator;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + oscarCount.hashCode() + usaBoxOffice.hashCode() + genre.hashCode() +
                mpaaRating.hashCode() + operator.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Movie) {
            Movie movieObj = (Movie) obj;
            return name.equals(movieObj.getName()) && coordinates.equals(movieObj.getCoordinates()) &&
                    (oscarCount == movieObj.getOscarCount()) && (usaBoxOffice == movieObj.getUsaBoxOffice()) &&
                    (genre == movieObj.getGenre()) && (mpaaRating == movieObj.getMpaaRating()) &&
                    operator.equals(movieObj.getOperator());
        }
        return false;
    }
}
