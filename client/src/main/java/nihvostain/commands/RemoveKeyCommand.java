
package nihvostain.commands;

import common.model.TypeOfElement;
import common.managers.*;
import common.utility.*;
import common.model.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * Удаление элемента по ключу
 */
public class RemoveKeyCommand implements Command {

    private final Communication communication;
    public RemoveKeyCommand(Communication communication) {
    this.communication = communication;
    }

    /**
     * @param args массив аргументов
     */
    @Override
    public Request request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.REMOVE_KEY, args);
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
    public InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, TimeoutException, ClassNotFoundException {
        byte [] message;
        Request request = new Request(TypeRequest.REQUEST_PARAM, TypeCommand.REMOVE_KEY, params);
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
