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
 * Команда добавления элемента в коллекцию с ключом
 */
public class InsertCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public InsertCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public void execute(Request request) throws IOException {

        String key = request.getParams().get(0);
        try {
            collectionManager.insert(key, request.getStudyGroup());
            if (request.getStudyGroup().getGroupAdmin() != null) {
                Person.addPassportID(request.getStudyGroup().getGroupAdmin().getPassportID());
            }
        } catch (ExistingKeyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Добавил " + collectionManager.getStudyGroupList().get(key));
        RequestObj req = new RequestObj("Добавил " + collectionManager.getStudyGroupList().get(key));
        communication.send(req.serialize());
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.STUDYGROUP;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 12;
    }

    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        String key = params.get(0);
        if (!collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.ExistingKey;
        }

    }

    @Override
    public boolean skipValidateField() {
        return false;
    }

}
