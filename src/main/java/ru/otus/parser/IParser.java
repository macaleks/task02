package ru.otus.parser;

import java.util.function.Consumer;

public interface IParser<T> {

    void parseCsv(String filename, Consumer<T> consumer);
}
