package nihvostain.managers;

import nihvostain.commands.*;
import common.managers.*;
import common.utility.*;
import common.model.*;
import common.exceptions.*;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.*;

/**
 * Класс вызывателя команд
 */
public class Invoker {

    private final CollectionManager collectionManager;

    private Communication communication;
    /*
     * Флаг файла
     */
    private boolean fileFlag = false;
    /**
     * Текущая глубина рекурсии
     */
    private static int depth;
    /**
     * Максимальная глубина рекурсии
     */
    private static final int maxDepth = 10;


    public Invoker(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        depth+=1;
    }

    /**
     * исполнение команд и сканирование ввода
     * @throws InputFromScriptException ошибка в скрипте
     * @throws RecursionDepthExceededException ошибка глубины рекурсии
     */
    public void scanning() throws InputFromScriptException, RecursionDepthExceededException, IOException {

        if (depth > maxDepth) {
            throw new RecursionDepthExceededException();
        }
        Map<TypeCommand, Command> commands = new LinkedHashMap<>();

        commands.put(TypeCommand.INFO, new InfoCommand(collectionManager, communication));
        commands.put(TypeCommand.SHOW, new ShowCommand(collectionManager, communication));
        commands.put(TypeCommand.INSERT, new InsertCommand(collectionManager, communication));
        commands.put(TypeCommand.UPDATE, new UpdateCommand(collectionManager, communication));
        commands.put(TypeCommand.REMOVE_KEY, new RemoveKeyCommand(collectionManager, communication));
        commands.put(TypeCommand.CLEAR, new ClearCommand(collectionManager, communication));
        commands.put(TypeCommand.EXIT, new ExitCommand(collectionManager, communication));
        commands.put(TypeCommand.REMOVE_LOWER, new  RemoveLowerCommand(collectionManager, communication ));
        commands.put(TypeCommand.REPLACE_IF_GREATER, new ReplaceIfGreaterCommand(collectionManager, communication));
        commands.put(TypeCommand.REMOVE_GREATER_KEY, new RemoveGreaterKeyCommand(collectionManager, communication));
        commands.put(TypeCommand.GROUP_COUNTING_BY_SEMESTER_ENUM, new GroupCountingBySemesterEnum(collectionManager, communication));
        commands.put(TypeCommand.FILTER_CONTAINS_NAME, new FilterContainsNameCommand(collectionManager, communication));
        commands.put(TypeCommand.FILTER_GREATER_THAN_GROUP_ADMIN, new FilterGreaterThanGroupAdminCommand(collectionManager, communication));

        Thread thread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine().trim();
                ArrayList<String> tokens = new ArrayList<>(List.of(line.split(" +")));
                if (tokens.get(0).equals("save")) {
                    new SaveCommand(collectionManager).execute(new Request());

                }
            }
        });
        thread.start();
        while (true) {

            byte[] message;

            message = communication.receive();
            Request request;

            try {

                request = new Deserialize<Request>(message).deserialize();
                if (request.getTypeRequest() == TypeRequest.REQUEST_COMMAND){
                    Command command = commands.get(request.getName());
                    if (command.isValidParam(request.getParams()) == InvalidParamMessage.TRUE) {
                        command.execute(request);
                    }
                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PARAM){
                    ResponseParam responseParam = new ResponseParam(commands.get(request.getName()).isValidParam(request.getParams()));
                    communication.send(responseParam.serialize());
                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PASSPORT) {
                    System.out.println("Паспорт" + request.getParams() + " " + Person.getPassportIDList().contains(request.getParams().get(0)));
                    if (Person.getPassportIDList().contains(request.getParams().get(0))) {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.FALSE);
                        communication.send(responseParam.serialize());
                    } else {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.TRUE);
                        communication.send(responseParam.serialize());
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("ошибка передачи данных с клиента");
            }


            /*try {
                request = new Deserialize<Request>(message).deserialize();
                System.out.println("сериализовал req");
            } catch (ClassNotFoundException | ClassCastException e) {
                try {
                    requestParam = new Deserialize<RequestParam>(message).deserialize();
                    ResponseParam responseParam = new ResponseParam(commands.get(requestParam.getName()).isValidParam(requestParam.getParams()));
                    System.out.println("отправил "+ Arrays.toString(responseParam.serialize()));
                    communication.send(responseParam.serialize());

                    continue;
                } catch (ClassNotFoundException ex) {
                    System.out.println("dksksk");
                    continue;
                }
            }

             */

        }
    }

    /**
     * Устанавливает флаг файла
     * @param fileFlag читаем ли из файла
     */
    public void setFileFlag(boolean fileFlag) {
        this.fileFlag = fileFlag;
    }

    /**
     * Установить текущую глубину рекурсии
     * @param depth рекурсия
     */
    public void setDepth(int depth){
        Invoker.depth = depth;
    }

}
