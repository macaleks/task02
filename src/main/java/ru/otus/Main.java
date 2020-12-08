package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.AnswerDao;
import ru.otus.dao.QuestionDao;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Student;
import ru.otus.service.CheckAnswerService;

import java.util.*;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);

        AnswerDao dao = ctx.getBean(AnswerDao.class);
        QuestionDao questionDao = ctx.getBean(QuestionDao.class);
        Student student = ctx.getBean(Student.class);

        Map<Integer, List<Answer>> allAnswers = dao.getMapOfAnswers();
        Map<Integer, Question> allQuestions = questionDao.getMapOfQuestions();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello!");
        System.out.println("Enter your name: ");
        student.setName(scanner.next());
        System.out.println("Enter your surname: ");
        student.setSurname(scanner.next());

        System.out.println(String.format("Hello, %s %s! Answer to 5 questions, please",
                student.getName(), student.getSurname()));
        System.out.println();
        for (Question question: allQuestions.values()) {
            System.out.println(String.format("%d. %s", question.getId(), question.getQuestion()));

            List<Answer> answers = allAnswers.get(question.getId());
            long correctAnswers = answers.stream().filter(Answer::isCorrect).count();
            System.out.printf("Select %d correct answers(press Enter after each selected answer):", correctAnswers);
            System.out.println();

            for (int i = 0; i < answers.size(); i++) {
                System.out.println(String.format("%d. %s", i, answers.get(i).getAnswer()));
            }

            Set<Answer> respond = new HashSet<>();
            for (int i = 0; i < correctAnswers; i++) {
                int var = scanner.nextInt();
                //Check that it is not out of range
                if (var >=0 && var < answers.size()) {
                    respond.add(answers.get(var));
                }
            }

            student.addQuestionResult(question, CheckAnswerService.check(respond, correctAnswers));
        }
        System.out.print("You have ");
        if (student.checkIfPassed()) {
            System.out.println("passed.");
        } else {
            System.out.println("not passed.");
        }
    }
}
