package nihvostain;

import common.managers.*;
import common.utility.*;
import common.model.*;
import common.exceptions.*;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в консоль для управления коллекцией StudyGroup");
        System.out.println("Название файла для чтения и записи в него должно передаваться при помощи переменной окружения MY_VAR");

        Coordinates.setFields(new FieldsCoordinate[]{FieldsCoordinate.X, FieldsCoordinate.Y});

        Person.setFields(new FieldsPerson[]{FieldsPerson.NAME, FieldsPerson.BIRTHDAY,
                FieldsPerson.PassportID, FieldsPerson.EyeCOLOR, FieldsPerson.HairCOLOR});

        StudyGroup.setFields(new FieldsStudyGroup[]{FieldsStudyGroup.NAME,
                FieldsStudyGroup.COORDINATES, FieldsStudyGroup.StudentsCount,
                FieldsStudyGroup.FormOFEducation, FieldsStudyGroup.SEMESTER, FieldsStudyGroup.GroupADMIN});


        Scanner sc = new Scanner(System.in);
        int timeout = 3000;
        int serverPort = 9898;  // Порт сервера (куда отправляем)
        int clientPort = 8889;
        int bufferCapacity = 10000;
        Communication communication = new Communication(serverPort, clientPort, bufferCapacity, timeout);
        Invoker invoker = new Invoker(sc, communication);
        try {
            System.out.print("~ ");
            invoker.scanning();
        } catch (InputFromScriptException | RecursionDepthExceededException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}