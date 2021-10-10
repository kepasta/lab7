package common.data;

import common.interaction.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Movie class. Is stored in the collection.
 */

public class Movie implements Comparable<Movie> {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Long oscarCount;
    private Long usaBoxOffice;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;
    private User owner;

    public Movie(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, Long oscarCount,
                 Long usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person operator, User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarCount = oscarCount;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
        this.owner = owner;
    }

    /**
     * @return ID of the movie.
     */
    public Long getId() {
        return id;
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
     * @return Creation date of the movie.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
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
     * @return Operator of the movie.
     */
    public Person getOperator() {
        return operator;
    }

    /**
     * @return Owner of the marine.
     */
    public User getOwner() {
        return owner;
    }


    public int compareTo(Movie MovieObj) {
        return id.compareTo(MovieObj.getId());
    }

    @Override
    public String toString() {
        String info = "";
        info += "Фильм №" + id;
        info += " [" + owner.getUsername() + " " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " в " + creationDate.format(DateTimeFormatter.ofPattern("HH:mm")) + "]";
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
                mpaaRating.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Movie) {
            Movie movieObj = (Movie) obj;
            return name.equals(movieObj.getName()) && coordinates.equals(movieObj.getCoordinates()) &&
                    (oscarCount == movieObj.getOscarCount()) && (usaBoxOffice == movieObj.getUsaBoxOffice()) &&
                    (genre == movieObj.getGenre()) && (mpaaRating == movieObj.getMpaaRating());
        }
        return false;
    }
}
