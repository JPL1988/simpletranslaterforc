package Project.inter;

import Project.Symbols.Type;

public class While extends Stmt {
    //bool语句
    Expr expr;
    //while循环里面待执行的语句
    Stmt stmt;
    //创建一个子节点为空的节点
    public While(){
        expr = null;
        stmt = null;
    }
    //初始化子节点
    public void init(Expr x,Stmt s){
        expr = x;
        stmt = s;
        if(expr.type != Type.Bool)
            expr.error("boolean required in while");
    }
    public void gen(int b,int a){
        after = a;//保存标号a
        expr.jumping(0,a);
        //用于进入下一次循环
        int label = newLabel();
        emitLabel(label);//生成stmt的标号
        stmt.gen(label,b);
        emit("goto L"+b);
    }
}
