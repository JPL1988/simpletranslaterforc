package Project.inter.expr;

import Project.Symbols.Type;
import Project.inter.Node;
import Project.lexer.Token;

public class Expr extends Node {
    //结点的运算符
    public Token op;
    //运算符类型
    public Type type;
    Expr(Token t,Type p){
        op=t;
        type=p;
    }
    public Expr gen(){
        return this;
    }
    public Expr reduce(){
        return this;
    }
    public void jumping(int t,int f){
        emitJumps(toString(),t,f);
    }

    public void emitJumps(String test, int t, int f) {
        if(t!=0&&f!=0){
            emit("if "+test + " goto L"+t);
            emit("goto L" + f);
        }
        else  if(t!=0){
            emit("if "+test+" goto L"+t);
        }
        else if(f!=0){
            emit("iffalse " +test+" goto L"+f);
        }
        else ;//不生成指令因为t和f都直接穿越
    }
    public String toString(){
        return op.toString();
    }
}
