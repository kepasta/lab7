package server.manager;

import common.exceptions.DatabaseHandlingException;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;

import common.data.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.manager.Outputer;

/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private TreeMap<Long, Movie> moviesCollection =  new TreeMap<>();
    private LocalDateTime lastInitTime;
    private DatabaseCollectionManager databaseCollectionManager;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager    ) {
        this.databaseCollectionManager = databaseCollectionManager;

        loadCollection();
    }

    /**
     * @return The collection itself.
     */
    public NavigableMap<Long, Movie> getCollection() {
        return moviesCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return moviesCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return moviesCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Movie getFirst() {
        if (moviesCollection.isEmpty()) return null;
        return moviesCollection.firstEntry().getValue();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public Movie getLast() {
        if (moviesCollection.isEmpty()) return null;
        return moviesCollection.lastEntry().getValue();
    }

    /**
     * @param id ID of the movie.
     * @return A movie by his ID or null if movie isn't found.
     */
    public Movie getById(Long id) {
        for (Movie movie : moviesCollection.values()) {
            if (movie.getId().equals(id)) return movie;
        }
        return null;
    }

    /**
     * @param movieToFind A movie who's value will be found.
     * @return A movie by his value or null if marine isn't found.
     */
    public Movie getByValue(Movie movieToFind) {
        for (Movie movie : moviesCollection.values()) {
            if (movie.equals(movieToFind)) return movie;
        }
        return null;
    }

    /**
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (moviesCollection.isEmpty()) return "Коллекция пуста!";
        return moviesCollection.values().stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    /**
     * Adds a new movie to collection.
     * @param movie A movie to add.
     */
    public void addToCollection(Movie movie) {
        moviesCollection.put(movie.getId(), movie);
    }

    /**
     * Removes a new movie to collection.
     * @param movie A movie to remove.
     */
    public void removeFromCollection(Movie movie) {
        moviesCollection.remove(movie);
    }

    /**
     * Remove movies greater than the selected one.
     * @param movieToCompare A marine to compare with.
     */
    public void removeGreater(Movie movieToCompare) {
        moviesCollection.values().removeIf(movie -> movie.compareTo(movieToCompare) > 0);
    }


    /**
     * Clears the collection.
     */
    public void clearCollection() {
        moviesCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Long generateNextId() {
        if (moviesCollection.isEmpty()) return 1L;
        return moviesCollection.lastEntry().getKey() + 1;
    }


    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        try {
            moviesCollection = databaseCollectionManager.getCollection();
            lastInitTime = LocalDateTime.now();
            Outputer.println("Коллекция загружена.");
        } catch (DatabaseHandlingException exception) {
            moviesCollection = new TreeMap<>();
            Outputer.printerror("Коллекция не может быть загружена!");
        }
    }

    @Override
    public String toString() {
        if (moviesCollection.isEmpty()) return "Коллекция пуста!";

        String info = "";
        for (Movie movie : moviesCollection.values()) {
            info += movie;
            if (movie != moviesCollection.lastEntry()) info += "\n\n";
        }
        return info;
    }
}
