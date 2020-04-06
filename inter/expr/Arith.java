package Project.inter.expr;

import Project.Symbols.Type;
import Project.lexer.Token;

/**
 * 实现了双目运算符，比如+和*
 */
public class Arith extends Op {
    public Expr expr1,expr2;
    public Arith(Token t,Expr p1,Expr p2){
        //t表示运算符的词法单元，null表示类型的占位符
        super(t,null);
        expr1 = p1;
        expr2 = p2;
        type = Type.max(expr1.type,expr2.type);//简单的类型检查
        if(type==null){
            error("type error");
        }
    }
    public Expr gen(){
        return new Arith(op,expr1.reduce(),expr2.reduce());
    }
    public String toString(){
        return expr1.toString()+" "+op.toString()+" "+expr2.toString();
    }
}
