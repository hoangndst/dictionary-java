package translator.Models;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Support {

    /**
     * Open link in default browser
     * @param link link to open
     */

    public static void OpenLink(String link) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}