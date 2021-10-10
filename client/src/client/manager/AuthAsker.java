package client.manager;

import client.StartClient;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.manager.Outputer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user a login and password.
 */
public class AuthAsker {
    private Scanner userScanner;

    public AuthAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Asks user a login.
     *
     * @return login.
     */
    public String askLogin() {
        String login;
        while (true) {
            try {
                Outputer.println("Введите логин:");
                Outputer.print(StartClient.PS2);
                login = userScanner.nextLine().trim();
                if (login.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Данного логина не существует!");
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("Имя не может быть пустым!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return login;
    }

    /**
     * Asks user a password.
     *
     * @return password.
     */
    public String askPassword() {
        String password;
        while (true) {
            try {
                Outputer.println("Введите пароль:");
                Outputer.print(StartClient.PS2);
                password = userScanner.nextLine().trim();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Неверный логин или пароль!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return password;
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     */
    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                Outputer.print(StartClient.PS2);
                answer = userScanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("Ответ не распознан!");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("Ответ должен быть представлен знаками '+' или '-'!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}