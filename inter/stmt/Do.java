package Project.inter.stmt;

import Project.Symbols.Type;
import Project.inter.Stmt;
import Project.inter.expr.Expr;

public class Do extends Stmt {
    //while中的bool判断
    Expr expr;
    //do里面的语句
    Stmt stmt;
    public Do(){
        expr=null;
        stmt=null;
    }
    public void init(Stmt s,Expr x){
        expr = x;
        stmt = s;
        if(expr.type != Type.Bool)
            expr.error("boolean required in do");
    }
    public void gen(int b,int a){
        after = a;
        int label = newLabel();//用于expr的标号
        stmt.gen(b,label);
        emitLabel(label);
        expr.jumping(b,0);
    }
}
