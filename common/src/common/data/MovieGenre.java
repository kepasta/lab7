package common.data;

/**
 * Enumeration with movie genres.
 */
public enum MovieGenre {
    ACTION,
    WESTERN,
    TRAGEDY,
    FANTASY;


    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (MovieGenre genre : values()) {
            nameList += genre.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
