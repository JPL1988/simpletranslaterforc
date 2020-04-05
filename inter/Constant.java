package Project.inter;

import Project.Symbols.Type;
import Project.lexer.Token;
import Project.lexer.TokenImpl.Num;
import Project.lexer.TokenImpl.Word;

public class Constant extends Expr {
    public static final Constant
            True = new Constant(Word.True,Type.Bool),
            False = new Constant(Word.False,Type.Bool);

    public Constant(Token t, Type p){
        super(t,p);
    }
    public Constant(int i){
        super(new Num(i),Type.Int);
    }

    /**
     *
     * @param t bool表达式的true出口
     * @param f bool表达式的false出口
     *          如果表达式为真，代码中就会包含一条目标为t的跳转指令。
     *          如果表达式为假，就会有一个目标为f的指令，
     *          按照惯例，特殊标号0，表示控制流从表达式穿越，到达表达式代码之后的下一个指令
     */
    public void jumping(int t,int f){
        if(this == True &&t != 0)
            emit("goto L"+t);
        else if(this==False && f != 0)
            emit("goto L"+f);
    }
}
