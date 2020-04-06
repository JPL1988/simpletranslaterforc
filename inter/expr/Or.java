package Project.inter.expr;

import Project.lexer.Token;

public class Or extends Logical {
    public Or(Token t, Expr x1, Expr x2){
        super(t,x1,x2);
    }

    /**
     * jumping生成了一个对应b = b1 || b2 的跳转代码
     * @param t
     * @param f
     * b1为真，则b的true 出口和b1的相同,b1为假时则穿过b1判断b2
     * b2为真，则b的true 出口和b2的相同，false出口也相同。
     * 变量label保证了b1的出口被正确的设置为b的代码的结尾处。
     * 如果t为0，那么label被设置为一个新的标号并且在b1和b2的代码被生成后在生成这个新标号
     */
    public void jumping(int t,int f){
        int label = (t != 0?t:newLabel());
        expr1.jumping(label,0);
        expr2.jumping(t,f);
        if(t==0)
            emitLabel(label);
    }
}
