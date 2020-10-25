package Interface;

enum Doctype {
    NDFL,
    Vacation,
    Maternity_leave
}

class Person {
    Person(String name2, String name1, String name3) {
        First_name = name1;
        Second_Name = name2;
        Middle_name = name3;
    }
    Person(){
        First_name = null;
        Second_Name = null;
        Middle_name = null;
    }
    private String First_name;
    private String Second_Name;
    private String Middle_name;
    private int INN;
}

public class Document {

    Document(int _id, Doctype _type, Person _person) {
        id = _id;
        type = _type;
        person = _person;
    }

    public void setId(int _id) {
        id = _id;
    }
    public void setPerson(Person _person) {
        person = _person;
    }
    public void setId(Doctype _type) {
        type = _type;
    }
    private Person person = new Person();
    private int id;
    private Doctype type;
}
