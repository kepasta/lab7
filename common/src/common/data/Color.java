package common.data;

/**
 * Enumeration with colors
 */
public enum Color {
    GREEN,
    RED,
    WHITE,
    BROWN,
    BLACK,
    YELLOW;


    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (Color color : values()) {
            nameList += color.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
