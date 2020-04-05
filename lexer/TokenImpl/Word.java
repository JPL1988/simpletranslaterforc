package Project.lexer.TokenImpl;

import Project.lexer.Tag;
import Project.lexer.Token;

/**
 * word用于保存保留字、标识符、和像&&这样的复合词法单元
 */
public class Word extends Token {
    //词素，即变量名
    public String lexeme = "";
    public Word(String str,int t){
        super(t);
        lexeme = str;
    }


    public static final Word
            and = new Word("&&", Tag.AND),or = new Word("||",Tag.OR),
            eq = new Word("==",Tag.EQ),ne = new Word("!=",Tag.NE),
            le = new Word("<=",Tag.LE),ge = new Word(">=",Tag.GE),
            minus = new Word("minus",Tag.MINUS),
            True = new Word("true",Tag.TRUE),
            False = new Word("false",Tag.FALSE),
            temp = new Word("t",Tag.TEMP);
    @Override
    public String toString() {
        return lexeme;
    }
}
