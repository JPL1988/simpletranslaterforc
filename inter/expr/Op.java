package Project.inter.expr;

import Project.Symbols.Type;
import Project.inter.expr.Expr;
import Project.inter.stmt.Temp;
import Project.lexer.Token;

public class Op extends Expr {

    public Op(Token t, Type p){
        super(t,p);
    }
    //生成零时变量并赋值
    public Expr reduce(){
        Expr x = gen();
        Temp t = new Temp(type);
        emit(t.toString()+" = "+x.toString());
        return t;
    }
}
