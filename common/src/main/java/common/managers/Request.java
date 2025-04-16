package common.managers;

import common.model.Person;
import common.model.StudyGroup;
import common.utility.TypeCommand;
import common.utility.TypeRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {

    private TypeRequest typeRequest;
    private TypeCommand name;
    private StudyGroup studyGroup = null;
    private Person person = null;
    private ArrayList<String> params;

    public Request(TypeRequest typeRequest, TypeCommand name, StudyGroup studyGroup, ArrayList<String> params) {
        this.typeRequest = typeRequest;
        this.name = name;
        this.studyGroup = studyGroup;
        this.params = params;
    }

    public Request(TypeRequest typeRequest, TypeCommand name, StudyGroup studyGroup) {
        this.typeRequest = typeRequest;
        this.name = name;
        this.studyGroup = studyGroup;
    }

    public Request(TypeRequest typeRequest, TypeCommand name) {
        this.typeRequest = typeRequest;
        this.name = name;
    }

    public Request(TypeRequest typeRequest, TypeCommand name, Person person) {
        this.typeRequest = typeRequest;
        this.name = name;
        this.person = person;
    }

    public Request(TypeRequest typeRequest, TypeCommand name, ArrayList<String> params) {
        this.typeRequest = typeRequest;
        this.name = name;
        this.params = params;
    }


    public Request(TypeRequest typeRequest, ArrayList<String> params) {
        this.typeRequest = typeRequest;
        this.params = params;
    }

    public Request() {

    }

    public byte[] serialize() throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();

    }

    public TypeCommand getName() {
        return name;
    }

    public void setName(TypeCommand name) {
        this.name = name;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public TypeRequest getTypeRequest() {
        return typeRequest;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Person getPerson() {
        return person;
    }
}
