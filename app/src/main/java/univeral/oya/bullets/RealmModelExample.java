package univeral.oya.bullets;

import io.realm.RealmObject;

// This is used to define getters/setters. By extending RealmObject, it makes the model functional
public class RealmModelExample extends RealmObject {
    private String name;
    private int age;

    //Configuring constructor -it is important to write one with no argument so you do not get an exception error

    public RealmModelExample(){

    }

    public RealmModelExample(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public int getAge(){
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
