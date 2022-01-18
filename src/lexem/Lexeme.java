package lexem;

public class Lexeme {

    public LexemeType lexemeType;
    public String value;

    public Lexeme (LexemeType lexemeType, String value) {
        this.lexemeType = lexemeType;
        this.value = value;
    }
    public Lexeme (LexemeType lexemeType, Character value) {
        this.lexemeType = lexemeType;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexemeType=" + lexemeType +
                ", value='" + value + '\'' +
                '}';
    }
}
