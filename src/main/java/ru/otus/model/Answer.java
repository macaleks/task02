package ru.otus.model;

import java.util.Objects;

public class Answer {
    private int id;
    private String answer;
    private boolean correct;

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return id == answer1.id &&
                correct == answer1.correct &&
                Objects.equals(answer, answer1.answer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, answer, correct);
    }
}
