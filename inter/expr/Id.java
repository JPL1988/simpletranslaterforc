package Project.inter.expr;

import Project.Symbols.Type;
import Project.inter.expr.Expr;
import Project.lexer.TokenImpl.Word;

public class Id extends Expr {
    //相对地址
    public int offset;

    public Id(Word id, Type p,int b){
        super(id,p);
        offset = b;
    }
}
