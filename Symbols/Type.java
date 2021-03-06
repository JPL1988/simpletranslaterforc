package Project.Symbols;


import Project.lexer.Tag;
import Project.lexer.TokenImpl.Word;

/**
 * 添加width的记录
 */
public class Type extends Word {
    //width用于存储分配
    public int width = 0;
    public Type(String s,int tag,int w){
        super(s,tag);
        width=w;
    }
    public static final Type
        Int = new Type("int", Tag.BASIC,4),
        Float = new Type("float",Tag.BASIC,8),
        Char = new Type("char",Tag.BASIC,1),
        Bool = new Type("bool",Tag.BASIC,1),
        Short = new Type("short",Tag.BASIC,2);

    /**
     * 判断是否是数字
     * @param p 要判断单词的类型
     * @return true or false
     */
    public static boolean numeric(Type p){
        if(Type.Char==p||Type.Int==p||Type.Float==p||p==Type.Short)
            return true;
        return false;
    }

    /**
     * 可用于类型转换时进行判断
     * @param p1 compare type 1
     * @param p2 compare type 2
     * @return  the max type of params
     */
    public static Type max(Type p1,Type p2){
        if(!numeric(p1)||!numeric(p2))
            return null;
        else if(p1==Type.Float||p2 == Type.Float)
            return Type.Float;
        else if(p1==Type.Int||p2 == Type.Int)
            return Type.Int;
        else if(p1==Type.Short||p2 ==Type.Short){
            return Type.Short;
        }
        else
            return Type.Char;
    }

}
