package ru.otus.parser;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import ru.otus.model.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class AnswerParser extends Parser implements IParser<Answer> {
    private final Map<String, BiConsumer<Answer, String>> mapper = new HashMap<>();

    public AnswerParser() {
        this.mapper.put("question_id", (o, v) -> o.setId(Integer.valueOf(v)));
        this.mapper.put("answer", Answer::setAnswer);
        this.mapper.put("correct", (o, v) -> o.setCorrect(BooleanUtils.toBoolean(v)));
    }

    @Override
    public void parseCsv(String filename, Consumer<Answer> consumer) {
        parseCsv(filename, Answer::new, mapper, consumer);
    }
}
