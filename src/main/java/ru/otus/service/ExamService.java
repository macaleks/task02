package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.AnswerDao;
import ru.otus.dao.QuestionDao;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Student;

import java.util.*;

@Service
public class ExamService {

    private final AnswerDao answerDao;
    private final QuestionDao questionDao;

    public ExamService(AnswerDao answerDao, QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
    }

    public String testStudent() {
        Map<Integer, List<Answer>> allAnswers = answerDao.getMapOfAnswers();
        Map<Integer, Question> allQuestions = questionDao.getMapOfQuestions();
        Student student = new Student();
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
        String result = String.format("%s %s has ", student.getName(), student.getSurname());
        if (student.checkIfPassed()) {
            result += "passed.";
        } else {
            result += "not passed.";
        }
        return result;
    }
}
