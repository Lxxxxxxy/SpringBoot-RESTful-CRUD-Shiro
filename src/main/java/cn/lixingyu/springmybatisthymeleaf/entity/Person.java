package cn.lixingyu.springmybatisthymeleaf.entity;

import java.io.Serializable;

/**
 * @author lxxxxxxy
 * @time 2019/07/31 17:30
 */
public class Person implements Serializable, Comparable<Person> {
    private Integer id;
    private String name;
    private Integer age;
    private Long likes;
    private String address;

    public Person(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", likes=" + likes +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person() {
    }

    public Person(String name, Integer age, Long likes, String address) {
        this.name = name;
        this.age = age;
        this.likes = likes;
        this.address = address;
    }

    public Person(Integer id, String name, Integer age, Long likes, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.likes = likes;
        this.address = address;
    }

    @Override
    public int compareTo(Person o) {
        return -1;
    }
}
