package translator.Models;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Link {

    /**
     * Open link in default browser
     * @param link link to open
     */

    public static void Open(String link) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}