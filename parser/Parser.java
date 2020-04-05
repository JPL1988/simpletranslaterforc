package Project.parser;

import Project.Symbols.*;
import Project.inter.*;
import Project.lexer.*;
import Project.lexer.TokenImpl.*;
import java.io.IOException;
import java.util.Map;

public class Parser  {
    //词法分析的分析器
    private Lexer lexer;
    //向前看词法单元
    private Token look;
    //当前或顶层的符号表
    Env top = null;
    //用于变量声明的存储位置
    int used = 0;
    //用于组合词法分析器，并开始分析
    public Parser(Lexer l )throws IOException{
        lexer = l;
        move();
    }
    //移入下一个向前看符号
    public void move()throws IOException{
        look  = lexer.scan();
    }
    private void error(String s){
        throw new Error("near line "+lexer.line+": "+s);
    }

    /**
     * 类型匹配
     * @param t 希望看见的类型
     * @throws IOException 如果出现不匹配，则是语法错误
     * 匹配到#则退出程序
     */
    private void match(int t) throws IOException{
        if(look.tag == t)
            move();
        else {
            error("syntax error");
        }
    }
    public void program() throws IOException{
        //语法分析，
        Stmt s = block();
        //用于生成中间代码
        int begin = s.newLabel();
        int after = s.newLabel();
        System.out.println();
        s.emitLabel(begin);
        s.gen(begin,after);
        s.emitLabel(after);
    }

    /**
     * block -> { decls stmts}
     * @return Stmt
     * @throws IOException
     */
    private Stmt block() throws IOException{
        match('{');
        Env savedEnv = top;
        //当前块的符号表
        top = new Env(top);
        decls();
        Stmt s = stmts();
        match('}');
        top = savedEnv;
        return s;
    }

    /**
     *  D -> type ID
     * @throws IOException
     */
    private void decls()throws IOException{
        while (look.tag == Tag.BASIC){
            Type  p = type();
            Token tok = look;
            match(Tag.ID);
            match(';');
            Id id = new Id((Word) tok,p,used);
            //存在问题，已解决。
            top.put(id.toString(),id);
            used = used+p.width;
        }
    }

    /**
     * 进行类型匹配，
     * @return
     * @throws IOException
     */
    private Type type() throws IOException{
        Type p = (Type) look;
        match(Tag.BASIC);//期望look.tag == Tag.BASIC
        if(look.tag!='[')//T -> basic
            return p;
        else
            return dims(p);//返回数组类型
    }
    private Type dims(Type p) throws IOException{
        match('[');
        Token tok = look;
        match(Tag.NUM);
        match(']');
        if(look.tag=='[')//匹配多维数组
            p=dims(p);
        return new Array(((Num)tok).value,p);
    }
    private Stmt stmts() throws  IOException{
        if(look.tag =='}')
            return Stmt.Null;
        else
            return new Seq(stmt(),stmts());
    }
    private Stmt stmt() throws  IOException{
        Expr x;
        Stmt s1,s2;
        //用于为break语句保存外层的循环语句
        Stmt savedStmt;
        switch (look.tag){
            case ';':
                move();
                return Stmt.Null;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                x=bool();
                match(')');
                s1=stmt();
                if(look.tag!=Tag.ELSE)
                    return new If(x,s1);
                match(Tag.ELSE);
                s2 =stmt();
                return new Else(x,s1,s2);
            case Tag.WHILE:
                While whileNode = new While();
                savedStmt =Stmt.Enclosing;//保存外层循环
                Stmt.Enclosing = whileNode;//保存当前循环
                match(Tag.WHILE);
                match('(');
                x = bool();
                match(')');
                s1 = stmt();
                whileNode.init(x,s1);
                Stmt.Enclosing = savedStmt;//重置Stmt.Enclosing
                return whileNode;
            case Tag.DO:
                Do doNode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = doNode;
                match(Tag.DO);
                s1 = stmt();
                match(Tag.WHILE);
                match('(');
                x= bool();
                match(')');
                match(';');
                doNode.init(s1,x);
                Stmt.Enclosing = savedStmt;//重置Stmt.Enclosing
                return doNode;
            case Tag.BREAK:
                match(Tag.BREAK);
                match(';');
                return new Break();
            case '{':
                return block();
            default:
                return assign();
        }
    }

    /**
     *  赋值语句
     * @return
     * @throws IOException
     */
    private Stmt assign() throws IOException{
        Stmt stmt;
        Token t = look;
        //assign的左部必须是ID类型
        match(Tag.ID);
        Id id=top.get(t.toString());
        if(id==null){
            error(t.toString()+" undeclared");
        }
        if(look.tag=='='){//s -> id = E;
            move();
            Expr t2 = bool();
            stmt = new Set(id,t2);
        }else {// s -> L = E;
            Access x = offset(id);
            match('=');
            stmt = new SetElem(x,bool());
        }
        match(';');
        return stmt;
    }

    private Expr bool()throws IOException{
        Expr x = join();
        while (look.tag == Tag.OR){
            Token tok = look;
            move();
            x=new Or(tok,x,join());
        }
        return x;
    }
    private Expr join() throws IOException{
        Expr x = equality();
        while (look.tag==Tag.AND){
            Token tok = look;
            move();
            x = new And(tok,x,equality());
        }
        return x;
    }
    private Expr equality() throws IOException{
        Expr x = rel();
        while (look.tag == Tag.EQ||look.tag == Tag.NE){
            Token tok = look;
            move();
            x = new Rel(tok,x,rel());
        }
        return x;
    }
    private Expr rel() throws IOException{
        Expr x = expr();
        switch (look.tag){
            case '<':
            case Tag.LE:
            case Tag.GE:
            case '>':
                Token tok = look;
                move();
                return new Rel(tok,x,expr());
            default:
                return x;
        }
    }
    private Expr expr()throws IOException{
        Expr x = term();
        while (look.tag=='+'||look.tag=='-'){
            Token tok = look;
            move();
            x=new Arith(tok,x,term());
        }
        return x;
    }
    private Expr term() throws IOException{
        Expr x = unary();
        while (look.tag=='*'||look.tag=='/'){
            Token tok = look;
            move();
            x=new Arith(tok,x,unary());
        }
        return x;
    }
    private Expr unary() throws IOException{
        if(look.tag=='-'){
            move();
            return new Unary(Word.minus,unary());
        }
        else if(look.tag=='!'){
            Token tok = look;
            move();
            return new Not(tok,unary());
        }
        else
            return factory();
    }
    private Expr factory()throws IOException{
        Expr x = null;
        switch (look.tag){
            case '(':
                move();
                x=bool();
                match(')');
                return x;
            case Tag.NUM:
                x= new Constant(look,Type.Int);
                move();
                return x;
            case Tag.REAl:
                x= new Constant(look,Type.Float);
                move();
                return x;
            case Tag.TRUE:
                x= Constant.True;
                move();
                return x;
            case Tag.FALSE:
                x= Constant.False;
                move();
                return x;
            case Tag.ID:
                String s = look.toString();
                Id id = top.get(s);
                if(id==null){
                    error(s+ " undeclared");
                }
                move();
                if(look.tag!='[')
                    return id;
                else
                    return offset(id);
            default:
                error("syntax error");
                return x;
        }
    }
    private Access offset(Id a) throws IOException{//I ->[E]|[E] I
        Expr i;
        Expr w;
        Expr t1,t2;
        Expr loc;
        Type type = a.type;
        match('[');
        i=bool();//第一个下标I -> [E]
        match(']');
        type = ((Array)type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'),i,w);
        loc=t1;
        while (look.tag=='['){//多维下标，I -> [E]I
            match('[');
            i = bool();
            match(']');
            type = ((Array)type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'),i,w);
            t2 = new Arith(new Token('+'),loc,t1);
            loc=t2;
        }
        return new Access(a,loc,type);
    }














}
