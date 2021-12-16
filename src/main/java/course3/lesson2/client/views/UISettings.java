package course3.lesson2.client.views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UISettings {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.LIGHT_GRAY;
    private static final int BORDER_THICKNESS = 1;
    private static final String PANE_CONTENT_TYPE = "text/plane";

    public static Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    public static Border getThickBorder() {
        return BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS);
    }

    public static Border getEmptyBorder() {
        return BorderFactory.createEmptyBorder();
    }

    public static String getPaneContentType() {
        return PANE_CONTENT_TYPE;
    }
}
