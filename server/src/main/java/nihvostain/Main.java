package nihvostain;


import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, RecursionDepthExceededException, InputFromScriptException {

        byte[] serializeReq = new byte[1024];

        CollectionManager collectionManager = new CollectionManager();
        collectionManager.load(System.getenv("MY_VAR"));
        int serverPort = 9898;
        int bufferCapacity = 10000;
        Communication communication = new Communication(serverPort, bufferCapacity);
        Invoker invoker = new Invoker(collectionManager, communication);
        invoker.scanning();



    }
}