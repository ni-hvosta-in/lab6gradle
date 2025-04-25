package nihvostain.commands;

import common.model.TypeOfElement;
import common.managers.*;
import common.utility.*;
import common.model.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Команда обновления элемента по заданному id
 */
public class UpdateCommand implements Command {

    Communication communication;
    public UpdateCommand(Communication communication) {
        this.communication = communication;
    }

    /**
     * @param args массив аргументов
     * @return
     */
    @Override
    public Request request (ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        String id = args.get(0);
        ArrayList<String> params = new ArrayList<>();
        args.remove(0);
        params.add(id);
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.UPDATE, new StudyGroup(args), params);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
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

    /**
     * @return кол-во параметров для функции
     */
    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    /**
     * @param params массив параметров для команды
     * @return валидность этих параметров
     */
    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, TimeoutException, ClassNotFoundException {
        byte [] message;
        Request request = new Request(TypeRequest.REQUEST_PARAM, TypeCommand.UPDATE, params);
        message = request.serialize();
        communication.send(message);
        byte[] response = communication.receive();
        return new Deserialize<ResponseParam>(response).deserialize().getParam();
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }


}
