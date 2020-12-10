package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Question;
import ru.otus.parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

public class QuestionDaoTest {

    Parser parser = mock(Parser.class);
    String filename;

    @Test
    public void test() {
        Map<Integer, Question> expected = new HashMap<>();
        expected.put(1, question(1, "What is the largest city in the UK?"));
        expected.put(2, question(2, "List two closest planets to the Sun"));

        QuestionDao dao = new QuestionDao(parser, filename);
        doAnswer(i -> {
            Consumer<Question> consumer = i.getArgument(1, Consumer.class);
            expected.values().stream().forEach(question -> consumer.accept(question));
            return null;
        }).when(parser).parseCsv(same(filename), any());

        Map<Integer, Question> result = dao.getMapOfQuestions();

        Assertions.assertEquals(expected, result);

        verify(parser, times(1)).parseCsv(same(filename), any());
    }

    private Question question(int id, String question) {
        Question a = new Question();
        a.setId(id);
        a.setQuestion(question);
        return a;
    }
}
