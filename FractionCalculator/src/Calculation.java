
import java.util.*;

public class Calculation {

    // list of available operators
    final String OPERATORS = "+-*:";
    // temporary stack that holds operators, functions and brackets
    Stack<Object> stackOperations = new Stack<>();
    // stack for holding expression converted to reversed polish notation
    Stack<Object> stackRPN = new Stack<>();

    public boolean isFraction(Object token) {
        return tryParse(token);
    }

    private boolean isOpenBracket(Object token) {
        return token.equals("(");
    }

    private boolean isCloseBracket(Object token) {
        return token.equals(")");
    }

    private boolean isOperator(Object token) {
        return OPERATORS.contains(token.toString());
    }

    private byte getPrecedence(Object token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 2;
    }

    public static void checkBracket(String token) {
        token = token.replace("[0-9]*", "");
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) == '(') {
                stack.push(token.charAt(i));
            } else if (token.charAt(i) == ')') {
                if (stack.isEmpty() || stack.peek() != '(')
                    throw new IllegalArgumentException("Некорректное выражение");
                stack.pop();
            }
        }
        if (!stack.isEmpty()) throw new IllegalArgumentException("Некорректное выражение");
    }

    public static boolean tryParse(Object token) {
        String[] nums = token.toString().split("/");
        return nums.length == 2;
    }

    public Stack<Object> parse(String expression) {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();
        // make some preparations

        checkBracket(expression);

        while (true) {
            String[] brackets = expression.split("\\(|\\)");
            if (brackets.length == 0) break;
            for (String bracket : brackets) {
                if (bracket.isEmpty() || bracket.equals(" ")) {
                    continue;
                }
                List<String> fractions = new ArrayList<>();
                fractions.addAll(Arrays.asList(bracket.split(" ")));
                for (int i = 0; i < fractions.size(); i++) {
                    if (!fractions.get(i).equals("")) {
                        if (isOpenBracket(fractions.get(i))) {
                            stackOperations.push(fractions.get(i));
                        } else if (isCloseBracket(fractions.get(i))) {
                            while (!stackOperations.empty() && !isOpenBracket(stackOperations.lastElement())) {
                                stackRPN.push(stackOperations.pop());
                            }
                            stackOperations.pop();
                        } else if (isFraction(fractions.get(i))) {
                            Fractions tmp = Fractions.parse(fractions.get(i));
                            stackRPN.push(tmp);
                        } else if (isOperator(fractions.get(i))) {
                            while (!stackOperations.empty() && isOperator(stackOperations.lastElement()) && getPrecedence(fractions.get(i)) <= getPrecedence(stackOperations.lastElement())) {
                                stackRPN.push(stackOperations.pop());
                            }
                            stackOperations.push(fractions.get(i));
                        }
                    }
                }

                while (!stackOperations.empty()) {
                    stackRPN.push(stackOperations.pop());
                }
                //System.out.println(stackRPN); //RPN
                //System.out.println(stackRPN.peek());
                //break;


            }
            break;
        }
        return (stackRPN);
    }
}

class CalculationRPN extends Calculation {
    Stack<Fractions> stackAnswer = new Stack<>();

    void calculations(Stack<Object> stackNPN) {
        try {
            for (Object item : stackNPN) {
                //System.out.println(item);

                if (Calculation.tryParse(item)) {
                    stackAnswer.push(Fractions.parse(item));
                } else {
                    Fractions tmp1 = Fractions.simplify(stackAnswer.pop());
                    Fractions tmp2 = Fractions.simplify(stackAnswer.pop());
                    switch (item.toString()) {
                        case "+" -> stackAnswer.push(Fractions.Addition(tmp2, tmp1));
                        case "-" -> stackAnswer.push(Fractions.Subtraction(tmp2, tmp1));
                        case "*" -> stackAnswer.push(Fractions.Multiplication(tmp2, tmp1));
                        case ":" -> stackAnswer.push(Fractions.Division(tmp2, tmp1));
                    }
                }
            }
        } catch (RuntimeException er) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        System.out.println(stackAnswer);

    }
}


