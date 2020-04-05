package Project.Symbols;
import Project.lexer.Tag;
public class Array extends Type {
    //数组元素类型
    public Type of;
    //元素个数
    public int size = 1;
    public Array(int sz,Type p){
        super("[]", Tag.INDEX,sz*p.width);
        size = sz;
        of = p;
    }

    @Override
    public String toString() {
        return "["+size+"]"+of.toString();
    }
}
