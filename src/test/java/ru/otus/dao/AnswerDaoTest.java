package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Answer;
import ru.otus.parser.IParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;


public class AnswerDaoTest {

    IParser parser = mock(IParser.class);
    String filename;

    @Test
    public void test() {
        Map<Integer, List<Answer>> expected = new HashMap<>();
        expected.put(1, Arrays.asList(answer(1, "Manchester", false),
                answer(1, "Lincoln", false)));
        expected.put(2, Arrays.asList(answer(2, "Earth", false),
                answer(2, "Mercury", true)));

        AnswerDao dao = new AnswerDao(parser, filename);
        doAnswer(i -> {
            Consumer<Answer> consumer = i.getArgument(1, Consumer.class);
            expected.values().stream().flatMap(List::stream).forEach(answer -> consumer.accept(answer));
            return null;
        }).when(parser).parseCsv(same(filename), any());

        Map<Integer, List<Answer>> result = dao.getMapOfAnswers();

        Assertions.assertEquals(expected, result);

        verify(parser, times(1)).parseCsv(same(filename), any());
    }

    private Answer answer(int questionId, String answer, boolean correct) {
        Answer a = new Answer();
        a.setId(questionId);
        a.setAnswer(answer);
        a.setCorrect(correct);
        return a;
    }
}
