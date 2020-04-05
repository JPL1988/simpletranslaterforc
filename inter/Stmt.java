package Project.inter;

public class Stmt extends Node {
    public Stmt(){}
    public static Stmt Null = new Stmt();
    //保存语句的下一条指令的标号。
    public int after = 0;
    //用于break语句，在语法分析时被用于跟踪外层。
    public static Stmt Enclosing =Stmt.Null;
    /**
     *
     * @param b 语句开始处的标号
     * @param a 语句的下一条指令的标号（标记这个语句代码之后的第一条指令）
     */
    public void gen(int b,int a){}
}
