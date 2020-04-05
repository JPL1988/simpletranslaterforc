package Project.inter;

import Project.Symbols.Type;
import Project.lexer.Token;

/**
 * 为OR,and,not 提供了一些功能
 */
public class Logical extends Expr {
    public Expr expr1,expr2;
    public Logical(Token t,Expr x1,Expr x2){
        super(t,null);//开始时类型设置为空
        expr1 = x1;
        expr2 = x2;
        type = check(expr1.type,expr2.type);
        if(type==null)
            error("type error");
    }
    public Type check(Type p1,Type p2){
        if(p1 == Type.Bool && p2 == Type.Bool)
            return Type.Bool;
        return null;
    }
    public Expr gen(){
        int f = newLabel();
        int a = newLabel();
        Temp temp = new Temp(type);
        this.jumping(0,f);
        emit(temp.toString()+" = true");
        emit("goto L"+a);
        emitLabel(f);
        emit(temp.toString()+" = false");
        emitLabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return expr1.toString()+" "+op.toString()+" "+expr2.toString();
    }
}
