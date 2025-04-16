package common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление всех цветов волос
 */
public enum HairColor {
    RED("красный"),
    BLACK("черный"),
    ORANGE("оранжевый"),
    WHITE("белый"),
    BROWN("коричневый");

    private final String col;

    HairColor (String col){
        this.col = col;
    }

    /**
     * @return словарь название цвета + элемент из enum'а
     */
    static public Map <String, HairColor> getColors(){
        Map <String, HairColor> colorMap = new HashMap<>();
        colorMap.put(RED.col, RED);
        colorMap.put(BLACK.col, BLACK);
        colorMap.put(ORANGE.col, ORANGE);
        colorMap.put(WHITE.col, WHITE);
        colorMap.put(BROWN.col, BROWN);

        return colorMap;
    }
}