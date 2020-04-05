package Project.inter;

import Project.lexer.Token;

/**
 * and的代码和or类似
 */
public class And extends Logical{
    public And(Token t,Expr x1,Expr x2){
        super(t, x1, x2);
    }

    /**
     *
     * @param t true 的出口
     * @param f false的出口
     * expr1 的true出口直接穿过，false出口与expr相同
     * expr2 的true出口与b2,false的出口与expr相同
     */
    public void jumping(int t,int f){
        int label = (f!=0 ? f : newLabel());
        expr1.jumping(0,label);
        expr2.jumping(t,f);
        if(f==0)
            emitLabel(label);
    }
}
