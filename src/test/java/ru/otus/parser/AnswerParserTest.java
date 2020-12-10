package ru.otus.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerParserTest {

    @Test
    public void test() {
        List<Answer> result = new ArrayList<>();
        AnswerParserImpl parser = new AnswerParserImpl();
        parser.parseCsv("csv/answers.csv", result::add);

        List<Answer> expected = new ArrayList<>();
        expected.add(answer(1, "Manchester", false));
        expected.add(answer(1, "Lincoln", false));
        expected.add(answer(2, "Earth", false));
        expected.add(answer(2, "Mercury", true));

        Assertions.assertEquals(expected, result);

    }

    private Answer answer(int questionId, String answer, boolean correct) {
        Answer a = new Answer();
        a.setId(questionId);
        a.setAnswer(answer);
        a.setCorrect(correct);
        return a;
    }
}
