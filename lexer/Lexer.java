package Project.lexer;

import Project.lexer.TokenImpl.Num;
import Project.lexer.TokenImpl.Real;
import Project.Symbols.Type;
import Project.lexer.TokenImpl.Word;

import java.io.IOException;
import java.util.*;

public class Lexer {
    //把字符串映射为字word，存储关键字和被定义的变量
    public Map<String, Token> words = new HashMap<>();
    //记录下一个char或空白。
    public char peek = ' ';
    //行数
    public static int line = 0;
    public Lexer(){
        reserve(new Word("if",   Tag.IF)    );
        reserve(new Word("else", Tag.ELSE)  );
        reserve(new Word("while",Tag.WHILE) );
        reserve(new Word("do",   Tag.DO)    );
        reserve(new Word("break",Tag.BREAK) );
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Char );
        reserve(Type.Int);
        reserve(Type.Bool);
        reserve(Type.Float);
    }

    /**
     * read a word from input stream
     * @throws IOException
     */
     void readch() throws IOException{
        peek = (char) System.in.read();
    }

    /**
     *
     * @param c 期待读入的下一个字符
     * @return 下一个字符与c是否相等
     * @throws IOException
     */
    public boolean readch(char c) throws IOException{
        readch();
        if(peek!=c)
            return false;
        peek = ' ';
        return true;
    }
    /***
     * 把关键字添加到words里面
     * @param w 保留关键字
     */
    private void reserve (Word w){
        words.put(w.lexeme,w);
    }

    /**
     *
     * @return 返回一个词素，只有在是字符串时才会加入map集合中
     */
    public Token scan() throws IOException {
        //清除空白及换行
        clearTab();
        //分析双目运算符
        Token cur = analysisSymbol();
        if(cur!=null){
            return cur;
        }
        //分析识别数字
        cur = analysisNum();
        if(cur!=null){
            return cur;
        }
        //分析识别字符串
        cur = analysisWord();
        if(cur!=null){
            return cur;
        }
        //peek中的任意字符都被当作词法单元返回
        Token t = new Token(peek);
        peek = ' ';
        return t;
    }

    /**
     *
     * @return token of symbol or null
     * @throws IOException
     */
    public Token analysisSymbol() throws IOException{
        switch (peek){
            case '&':
                if(readch('&'))
                    return Word.and;
                else
                    return new Token('&');
            case '|':
                if(readch('|'))
                    return Word.or;
                else
                    return new Token('|');
            case '=':
                if(readch('='))
                    return Word.eq;
                else
                    return new Token('=');
            case '!':
                if(readch('='))
                    return Word.ne;
                else
                    return new Token('!');
            case '<':
                if(readch('='))
                    return Word.le;
                else
                    return new Token('<');
            case '>':
                if(readch('='))
                    return Word.ge;
                else
                    return new Token('>');
            default:
                return null;
        }
    }
    /**
     *
     * @return Token of word or null
     */
    public Token analysisWord() throws IOException{
        if(Character.isLetter(peek)){
            StringBuffer sb = new StringBuffer();
            do{
                sb.append(peek);
                readch();
            }while (Character.isLetterOrDigit(peek));
            String str = sb.toString();
            Word w = (Word) words.get(str);
            if(w!=null){//区分保留字与描述符
                return w;
            }
            w = new Word(str,Tag.ID);
            words.put(str,w);
            return w;
        }
        return null;
    }

    /**
     *
     * @return Token of Num or null
     */
    public Token analysisNum() throws IOException{
        if(Character.isDigit(peek)){
            int value = 0;
            do{
                value = 10*value+Character.digit(peek,10);
                readch();
            }while (Character.isDigit(peek));
            if(peek!='.')
                return new Num(value);
            //处理浮点数
            float x = value;
            float d = 10;
            for (;;){
                readch();
                if(!Character.isDigit(peek))
                    break;
                x = x+Character.digit(peek,10)/d;
                d=d*10;
            }
            return new Real(x);
        }
        return null;
    }
    /**
     * 清除空格以及制表符
     */
    public void clearTab() throws IOException{
        //剔除空白，制表符，换行符
        for (;;readch()){
            if(peek==' '||peek=='\t')
                continue;
            else if(peek=='\n')
                line++;
            else break;
        }
    }
}
