package nihvostain.commands;

import common.managers.*;
import common.utility.*;
import common.model.*;
import common.exceptions.*;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Команда удаления элементов, ключ которых больше заданных
 */
public class RemoveGreaterKeyCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public RemoveGreaterKeyCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public void execute(Request request) throws IOException {

        String key = request.getParams().get(0);
        collectionManager.getStudyGroupList().keySet().removeIf(str -> str.compareTo(key) > 0);
        new ShowCommand(collectionManager, communication).execute(request);

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.PRIMITIVE;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 1;
    }

    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }
}
