package ru.otus.model;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Student {

    private String name;
    private String surname;
    private final Map<Question, Boolean> questionResult = new HashMap<>();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void addQuestionResult(Question question, Boolean result) {
        questionResult.put(question, result);
    }

    public boolean checkIfPassed() {
        return questionResult.values().stream().filter(a -> a == true).count() > 2;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
