package Project.lexer;

/**
 * 定义可以识别的类型
 */
public class Tag {
    //INDEX,MINUS,TEMP不是词法单元，将用于抽象语法树。
    //eq == ,le <=,ge>=,ne !=
    //变量的各种类型通过整型数字标识。
    public final static int
            AND   = 256, BASIC = 257, BREAK = 258, DO   = 259, ELSE  = 260,
            EQ    = 261, FALSE = 262, GE    = 263, ID   = 264, IF    = 265,
            INDEX = 266, LE    = 267, MINUS = 268, NE   = 269, NUM   = 270,
            OR    = 271, REAl  = 272, TEMP  = 273, TRUE = 274, WHILE = 275,
            END = 280;
}
