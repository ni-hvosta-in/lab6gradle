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
 * Команда вывода коллекции на экран
 */
public class ShowCommand implements Command {

    private final Communication communication;
    public ShowCommand(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void request(ArrayList<String> args) throws IOException, ClassNotFoundException, TimeoutException {

        Request request = new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.SHOW, args);
        communication.send(request.serialize());
        byte[] message = communication.receive();
        System.out.println(new Deserialize<RequestObj>(message).deserialize().getRequest());

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
        return 0;
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
        return false;
    }

}
