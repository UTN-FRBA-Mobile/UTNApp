package Calendario;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class Calendario {

    public Item[] items;

    public static Calendario getInstanceFromAsset(InputStream jsonFile) {
        Calendario calendario = new Calendario();
        calendario.items = new Gson().fromJson(getJsonString(jsonFile), Item[].class);

        return calendario;
    }

    private static String getJsonString(InputStream is) {
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
