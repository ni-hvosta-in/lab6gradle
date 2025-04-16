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
 * Команда удаления элементов меньших заданному
 */
public class RemoveLowerCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;

    public RemoveLowerCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public void execute(Request request) throws IOException {

        //Map<String,StudyGroup> studyGroupList = new LinkedHashMap<>();
        //StudyGroup studyGroup = new StudyGroup(0L, args); ВНИМАНИЕ
        StudyGroup studyGroup = request.getStudyGroup();
        if (!collectionManager.getStudyGroupList().isEmpty()) {

            collectionManager.getStudyGroupList().values().removeIf(st -> st.compareTo(studyGroup) < 0);
            new ShowCommand(collectionManager, communication).execute(request);
            /*
            for (Map.Entry<String, StudyGroup> pair : collectionManager.getStudyGroupList().entrySet()) {
                if (studyGroup.compareTo(pair.getValue()) < 0){
                    studyGroupList.put(pair.getKey(), pair.getValue());
                }
            }
            collectionManager.setStudyGroupList(studyGroupList);
             */
        }
        else {
            System.out.println("Коллекция пуста");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
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
        return 11;
    }

    @Override
    public Integer getNeededParamLen() {
        return 0;
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return true;
    }
}
