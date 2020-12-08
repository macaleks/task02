package ru.otus.parser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Parser {

    public <T> void parseCsv(String filename, Supplier<T> supplier, Map<String,
            BiConsumer<T, String>> mapper, Consumer consumer) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        try (Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(filename).toURI()));
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(0)
                     .withCSVParser(parser)
                     .build()) {
            String[] headers = csvReader.readNext();
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String[] line = iterator.next();
                buildModel(headers, line, supplier, mapper, consumer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> void buildModel(String[] headers, String[] line, Supplier<T> supplier, Map<String,
            BiConsumer<T, String>> mapper, Consumer consumer) {
        T object = supplier.get();
        for (int i = 0; i < headers.length; i++) {
            BiConsumer<T, String> setter = mapper.get(headers[i]);
            if (setter != null)
                setter.accept(object, line[i]);
        }
        consumer.accept(object);
    }
}
