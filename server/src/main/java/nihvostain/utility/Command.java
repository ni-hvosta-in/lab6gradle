package nihvostain.utility;

import common.managers.*;
import common.utility.*;
import common.model.*;
import common.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * интерфейс описывающий поведение команды
 */
public interface Command {
    /**
     * Выполняет команду
     * @param request запрос с клиента
     */
    void execute(Request request) throws IOException;

    /**
     * Возвращает описание
     * @return описание
     */
    String description();

    /**
     * Возвращает класс, который нужен для выполнения
     * @return класс, который будет вводиться
     */
    TypeOfElement getElementType();

    /**
     * Возвращает размер требуемого массива аргументов
     * @return размер массива аргументов
     */
    Integer getNeededArgsLen();

    /**
     * @return необходимая длина массива параметров
     */
    Integer getNeededParamLen();

    /**
     * @param params массив параметров для команды
     * @return валидность параметров
     */
    InvalidParamMessage isValidParam(ArrayList<String> params);

    /**
     * @return нужно ли пропускать валидность полей
     */
    boolean skipValidateField();
}
