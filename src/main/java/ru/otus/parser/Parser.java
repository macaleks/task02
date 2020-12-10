package ru.otus.parser;

import java.util.function.Consumer;

public interface Parser<T> {

    void parseCsv(String filename, Consumer<T> consumer);
}
