

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите операцию, используя пробелы между операциями: ");
                String operation = scanner.nextLine();
                if (operation.equals("quit")) break;
                Calculation infixToPostfix = new Calculation();
                CalculationRPN calculation = new CalculationRPN();
                calculation.calculations(infixToPostfix.parse(operation));
            } catch (Exception ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }
        }
        scanner.close();
        System.out.println("Завершение работы");
    }
}
