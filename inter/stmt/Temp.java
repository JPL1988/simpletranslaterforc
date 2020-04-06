package Project.inter.stmt;

import Project.Symbols.Type;
import Project.inter.expr.Expr;
import Project.lexer.TokenImpl.Word;

public class Temp extends Expr {
    static int count = 0;
    private int number = 0;
    public Temp(Type p){
        super(Word.temp,p);
        number = ++count;
    }
    public String toString(){
        return "t"+number;
    }
}
