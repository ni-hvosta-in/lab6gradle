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
 * Команда замена значения по ключу, если новое значение больше старого
 */
public class ReplaceIfGreaterCommand implements Command {

    final private Communication communication;

    public ReplaceIfGreaterCommand(Communication communication) {
        this.communication = communication;
    }

    /**
     * @param args массив аргументов
     */
    @Override
    public void request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        String key = args.get(0);
        ArrayList<String> params = new ArrayList<>();
        params.add(key);
        args.remove(0);
        Request request = new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.REPLACE_IF_GREATER, new StudyGroup(args), params);
        communication.send(request.serialize());
        byte[] message = communication.receive();
        System.out.println(new Deserialize<RequestObj>(message).deserialize().getRequest());
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
    public InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, TimeoutException, ClassNotFoundException {
        byte [] message;
        Request request = new Request(TypeRequest.REQUEST_PARAM, TypeCommand.REPLACE_IF_GREATER, params);
        message = request.serialize();
        communication.send(message);
        byte[] response = communication.receive();
        System.out.println(Arrays.toString(response));
        return new Deserialize<ResponseParam>(response).deserialize().getParam();
    }

    @Override
    public boolean skipValidateField() {
        return true;
    }
}
