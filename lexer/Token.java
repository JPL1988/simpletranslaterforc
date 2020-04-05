package Project.lexer;

/**
 * 记录词素的类型，所有关键字，保留字，标识符皆继承此类
 */
public class Token {
    //记录变量类型
    public final int tag;
    public Token(int t){
        tag=t;
    }

    @Override
    public String toString() {
        return ""+(char)tag;
    }
}
