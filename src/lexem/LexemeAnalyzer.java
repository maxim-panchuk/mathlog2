package lexem;

import java.util.ArrayList;
import java.util.List;

public class LexemeAnalyzer {
    public List<Lexeme> lexAnalyze (String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '&':
                    lexemes.add(new Lexeme(LexemeType.OP_CON, c));
                    pos++;
                    continue;
                case '|':
                    lexemes.add(new Lexeme(LexemeType.OP_DIS, c));
                    pos++;
                    continue;
                case '-':
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(c);
                    pos++;
                    stringBuilder.append('>');
                    pos++;
                    lexemes.add(new Lexeme(LexemeType.OP_IMPL, stringBuilder.toString()));
                    continue;
                case '!':
                    lexemes.add(new Lexeme(LexemeType.OP_NEG, c));
                    pos++;
                    continue;
                default:
                    StringBuilder stringBuilder1 = new StringBuilder();
                    do {
                        if (pos >= expText.length()) break;
                        c = expText.charAt(pos);
                        if (isVarChar(c)) {
                            stringBuilder1.append(c);
                            pos++;
                        }
                    } while (isVarChar(c));
                    lexemes.add(new Lexeme(LexemeType.VAR, stringBuilder1.toString()));
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, " "));
        return lexemes;
    }
    private static boolean isVarChar (Character c) {
        return (c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == 39);
    }
}
