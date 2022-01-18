import lexem.Lexeme;
import lexem.LexemeAnalyzer;
import lexem.LexemeBuffer;
import model.Counter;
import util.Node;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer();
        List<Lexeme> lexemeList = lexemeAnalyzer.lexAnalyze(expression);
        Counter counter = new Counter();
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemeList);
        Node node = counter.expr(lexemeBuffer);

        Map<String, Integer> vars = counter.vars;
        int len = vars.size();
        Set<String> setKeys = vars.keySet();
        ArrayList<String> names = new ArrayList<>(setKeys);

        int a = -1;

        for (int i = 0; i < Math.pow(2, len); i++) {
            int j = 0;
            vars.clear();
            while (j < names.size()) {
                vars.put(names.get(j), (i>>j)&1);
                j++;
            }
            if (a == -1) {
                a = result(node, vars);
            } else {
                if (a != result(node, vars)) {
                    System.out.println("Satisfiable and invalid");
                    a = -1;
                    break;
                }
            }
        }
        if (a == 0) {
            System.out.println("Unsatisfiable");
        } else if (a == 1) {
            System.out.println("Valid");
        }
    }

    public static int result (Node node, Map<String, Integer> values) {
        if (node == null) return -1;
        switch (node.nodeType) {
            case IMPL:
                if (result(node.leftChild, values) == 0) return 1;
                else if (result(node.rightChild, values) == 0) return 0;
                else return 1;
            case DIS:
                return result(node.leftChild, values) | result(node.rightChild, values);
            case CON:
                return result(node.leftChild, values) & result(node.rightChild, values);
            case NEG:
                if (result(node.leftChild, values) == 0) return 1;
                else return 0;
            default:
                return values.get(node.data);
        }
    }
}