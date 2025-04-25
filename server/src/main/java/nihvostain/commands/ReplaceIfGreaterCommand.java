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
 * Команда замена значения по ключу, если новое значение больше старого
 */
public class ReplaceIfGreaterCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public ReplaceIfGreaterCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public void execute(Request request) throws IOException {
        String key = request.getParams().get(0);
        if (collectionManager.getStudyGroupList().containsKey(key)){
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setID(collectionManager.getStudyGroupList().get(key).getId());
            if (studyGroup.compareTo(collectionManager.getStudyGroupList().get(key)) > 0){
                collectionManager.updateStudyGroup(key, studyGroup);
                RequestObj req = new RequestObj("заменил");
                communication.send(req.serialize());
            } else {
                RequestObj req = new RequestObj("не заменил");
                communication.send(req.serialize());
            }
        } else {
            System.out.println("Такого ключа не существует");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого";
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
        if (collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.NoKey;
        }
    }

    @Override
    public boolean skipValidateField() {
        return true;
    }
}
