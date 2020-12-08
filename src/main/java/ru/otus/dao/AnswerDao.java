package ru.otus.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.model.Answer;
import ru.otus.parser.IParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class AnswerDao {

    private final IParser<Answer> parser;
    private final String filename;

    public AnswerDao(@Qualifier("answerParser") IParser<Answer> parser, @Value("${answer}") String filename) {
        this.parser = parser;
        this.filename = filename;
    }

    public Map<Integer, List<Answer>> getMapOfAnswers() {
        List<Answer> answers = new ArrayList<>();
        parser.parseCsv(filename, answers::add);
        return answers.stream()
                .collect(groupingBy(Answer::getId));
    }
}
