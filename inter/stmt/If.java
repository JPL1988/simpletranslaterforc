package Project.inter.stmt;

import Project.Symbols.Type;
import Project.inter.expr.Expr;

public class If extends Stmt {
    //if语句的判断，bool
    Expr expr;
    //if语句里面要执行的语句
    Stmt stmt;
    public If(Expr e,Stmt s){
        expr = e;
        stmt = s;
        if(expr.type != Type.Bool)
            expr.error("boolean required in if");
    }
    public void gen(int b,int a){
        int label = newLabel();//stmt的代码标号
        expr.jumping(0,a);//为真时控制流穿越，为假时转向a
        emitLabel(label);
        stmt.gen(label,a);
    }
}
