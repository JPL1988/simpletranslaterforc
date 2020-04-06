package Project.inter;

import Project.lexer.Lexer;

/**
 * 抽象语法树的结点被实现为Node的对象。
 * 记录节点行号用于报告错误
 * 记录标签用于三地址代码生成
 */
public class Node {
    //记录本节点在原程序中的行号，便于报告错误
    int lexline = 0;
    //用于生成3地址代码，记录标号
    public static int labels = 0;

    public Node() {
        lexline = Lexer.line;
    }

    /**
     *
     * @return a new label
     */
    public int newLabel(){
        return ++labels;
    }

    /**
     * 输出标号（定义标号）
     * @param i 标号
     */
    public void emitLabel(int i){
        System.out.print("L"+i+":");
    }

    /**
     *添加一个tab键，在标记后
     * @param s next expr
     */
    public void emit(String s){
        System.out.println("\t"+s);
    }
    /**
     * 报告错误行号
     * @param s 错误提示语句
     */
    public void error(String s){
        throw new Error("near line "+lexline+": "+s);
    }

}
