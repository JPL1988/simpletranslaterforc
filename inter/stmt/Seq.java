package Project.inter.stmt;

import Project.inter.Stmt;

public class Seq extends Stmt {
    Stmt stmt1;
    Stmt stmt2;

    public Seq(Stmt stmt1, Stmt stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    /**
     * 重写父类方法，用于为stmt1,stmt2生成三地址代码
     * @param b 语句开始处的标号
     * @param a 语句的下一条指令的标号（标记这个语句代码之后的第一条指令）
     */
    @Override
    public void gen(int b, int a) {
        if(stmt1 == Stmt.Null)
            stmt2.gen(b,a);
        else if(stmt2 == Stmt.Null)
            stmt1.gen(b,a);
        else {
            int label = newLabel();
            stmt1.gen(b,label);
            emitLabel(label);
            stmt2.gen(label,a);
        }
    }
}
