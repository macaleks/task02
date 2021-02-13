package ru.otus.service;

import ru.otus.model.Answer;

import java.util.Set;

public class CheckAnswerService {

    public static boolean check(Set<Answer> respond, long correctAnswers) {
        long correctAnswersGiven = respond.stream()
                .filter(it -> it != null)
                .filter(Answer::isCorrect)
                .count();

        if (correctAnswersGiven == correctAnswers) {
            return true;
        }
        return false;
    }
}
