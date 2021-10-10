package server.manager;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.NoSuchElementException;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import common.data.Movie;

/**
 * Operates the file for saving/loading collection.
 */
public class FileManager {
    private Gson gson = new Gson();
    private File file;

    public FileManager(String fileName) {
        file = new File(fileName);
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */

    public void writeCollection(TreeMap<Long, Movie> collection) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);

        writer.write(gson.toJson(collection));
        writer.close();
    }

    /**
     * Reads collection from a file.
     * @return Read collection.
     */

    public TreeMap<Long, Movie> readCollection() throws FileNotFoundException {
        TreeMap<Long, Movie> result;
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Type collectionType = new TypeToken<TreeMap<Long, Movie>>() {}.getType();
        result = gson.fromJson(reader, collectionType);
        return result;
    }


    @Override
    public String toString() {
        String string = "FileManager (класс для работы с загрузочным файлом)";
        return string;
    }
}
