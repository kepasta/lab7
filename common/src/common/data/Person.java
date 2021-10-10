package common.data;

import java.io.Serializable;

/**
 * Operator of the movie.
 */
public class Person implements Serializable {
    private String name;
    private Float height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;

    public Person(String name, Float height, Color eyeColor, Color hairColor, Country nationality) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public Float getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return name + " (рост " + height + ", цвет глаз " + eyeColor + ", цвет волос " + hairColor + ", национальность " + nationality + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + height.hashCode() + eyeColor.hashCode() + hairColor.hashCode() + nationality.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Person) {
            Person personObj = (Person) obj;
            return name.equals(personObj.getName()) && (height == personObj.getHeight()) && (eyeColor == personObj.getEyeColor()) &&
                    (hairColor == personObj.getHairColor()) && nationality.equals(personObj.getNationality());
        }
        return false;
    }
}
