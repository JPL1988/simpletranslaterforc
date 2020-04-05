package Project.lexer.TokenImpl;

import Project.lexer.Token;
import Project.lexer.Tag;

public class Num extends Token {
    //记录整数
    public  final int value;
    public Num(int v){
        super(Tag.NUM);
        value=v;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
