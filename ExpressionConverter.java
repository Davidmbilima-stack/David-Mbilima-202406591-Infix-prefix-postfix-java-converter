import java.util.Stack;
import java.util.Scanner;

public class ExpressionConverter {

    // Function to check if character is operator
    static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }

    // Function to check precedence
    static int precedence(char c) {
        switch (c) {
            case '^': return 3;
            case '*':
            case '/': return 2;
            case '+':
            case '-': return 1;
        }
        return -1;
    }

    // INFIX TO POSTFIX
    static String infixToPostfix(String exp) {
        String result = "";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            // If operand
            if (Character.isLetterOrDigit(ch)) {
                result += ch;
            }
            // If '('
            else if (ch == '(') {
                stack.push(ch);
            }
            // If ')'
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                stack.pop(); // remove '('
            }
            // If operator
            else if (isOperator(ch)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    result += stack.pop();
                }
                stack.push(ch);
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    // INFIX TO PREFIX
    static String infixToPrefix(String exp) {
        // Reverse expression
        StringBuilder input = new StringBuilder(exp);
        input.reverse();

        // Swap brackets
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(')
                input.setCharAt(i, ')');
            else if (input.charAt(i) == ')')
                input.setCharAt(i, '(');
        }

        // Convert to postfix
        String postfix = infixToPostfix(input.toString());

        // Reverse result
        StringBuilder result = new StringBuilder(postfix);
        return result.reverse().toString();
    }

    // POSTFIX TO INFIX
    static String postfixToInfix(String exp) {
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                stack.push(ch + "");
            } else if (isOperator(ch)) {
                String op2 = stack.pop();
                String op1 = stack.pop();
                String newExp = "(" + op1 + ch + op2 + ")";
                stack.push(newExp);
            }
        }

        return stack.pop();
    }

    // PREFIX TO INFIX
    static String prefixToInfix(String exp) {
        Stack<String> stack = new Stack<>();

        for (int i = exp.length() - 1; i >= 0; i--) {
            char ch = exp.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                stack.push(ch + "");
            } else if (isOperator(ch)) {
                String op1 = stack.pop();
                String op2 = stack.pop();
                String newExp = "(" + op1 + ch + op2 + ")";
                stack.push(newExp);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter expression:");
        String expression = sc.nextLine();

        System.out.println("\nChoose Conversion:");
        System.out.println("1. Infix to Postfix");
        System.out.println("2. Infix to Prefix");
        System.out.println("3. Postfix to Infix");
        System.out.println("4. Prefix to Infix");

        int choice = sc.nextInt();

        String result = "";

        switch (choice) {
            case 1:
                result = infixToPostfix(expression);
                break;

            case 2:
                result = infixToPrefix(expression);
                break;

            case 3:
                result = postfixToInfix(expression);
                break;

            case 4:
                result = prefixToInfix(expression);
                break;

            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("Result: " + result);
    }
}