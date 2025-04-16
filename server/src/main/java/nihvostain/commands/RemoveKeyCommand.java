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
 * Удаление элемента по ключу
 */
public class RemoveKeyCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public RemoveKeyCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public void execute(Request request) throws IOException {
        if (collectionManager.getStudyGroupList().get(request.getParams().get(0)).getGroupAdmin() != null) {
            Person.removePassportID(collectionManager.getStudyGroupList().get(request.getParams().get(0)).getGroupAdmin().getPassportID());
        }
        String response = collectionManager.getStudyGroupList().get(request.getParams().get(0)).toString();
        collectionManager.removeKey(request.getParams().get(0));
        RequestObj req = new RequestObj("удалил по ключу " + request.getParams().get(0) + "\n" + response );
        communication.send(req.serialize());
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
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
        String key = params.get(0);
        if (collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.NoKey;
        }

    }

    @Override
    public boolean skipValidateField() {
        return false;
    }

}
