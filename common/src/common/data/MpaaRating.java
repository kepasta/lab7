package common.data;

/**
 * Enumeration with MPAA ratings;
 */
public enum MpaaRating {
    PG_13,
    R,
    NC_17;


    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (MpaaRating rating : values()) {
            nameList += rating.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
