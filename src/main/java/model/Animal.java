package model;

public class Animal {
    private String name;
    private int age;
    private String description;
    private String sex;
    private String type;
    private AnimalOwner owner;

    public Animal(String name, int age, String description, String sex, String type, AnimalOwner owner) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.sex = sex;
        this.type = type;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AnimalOwner getOwner() {
        return owner;
    }

    public void setOwner(AnimalOwner owner) {
        this.owner = owner;
    }



    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", sex='" + sex + '\'' +
                ", type='" + type + '\'' +
                ", owner=" + owner +
                '}';
    }
}
