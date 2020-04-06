package Project.inter.expr;

import Project.Symbols.Type;
import Project.lexer.Tag;
import Project.lexer.TokenImpl.Word;

public class Access extends Op {
    public Id array;
    public Expr index;

    /**
     *
     * @param a
     * @param i
     * @param p 将数组平坦化后的元素类型
     */
    public Access(Id a, Expr i, Type p){
        super(new Word("[]", Tag.INDEX),p);
        array = a;
        index = i;
    }
    public Expr gen(){
        return new Access(array,index.reduce(),type);
    }
    public  void jumping (int t,int f){
        emitJumps(reduce().toString(),t,f);
    }
    public String toString(){
        return array.toString() + " [ "+ index.toString()+" ]";
    }
}
