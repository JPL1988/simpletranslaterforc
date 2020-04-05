package Project.lexer.TokenImpl;

import Project.lexer.Tag;
import Project.lexer.Token;

/**
 * 用于记录浮点数
 */
public class Real extends Token {
    //记录浮点数
    public final float value;
    public Real(float v){
        super(Tag.REAl);
        value = v;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
