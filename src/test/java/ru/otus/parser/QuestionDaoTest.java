package ru.otus.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDaoTest {

    @Test
    public void test() {
        List<Question> result = new ArrayList<>();
        QuestionParser parser = new QuestionParser();
        parser.parseCsv("csv/questions.csv", result::add);

        List<Question> expected = new ArrayList<>();
        expected.add(question(1,"What is the largest city in the UK?"));
        expected.add(question(2,"List two closest planets to the Sun"));

        Assertions.assertEquals(expected, result);
    }

    private Question question(int id, String question) {
        Question a = new Question();
        a.setId(id);
        a.setQuestion(question);
        return a;
    }
}
