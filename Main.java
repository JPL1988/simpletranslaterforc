package Project;

import Project.lexer.Lexer;
import Project.parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "test.text";
        Lexer lexer = new Lexer(fileName);
        Parser parser = new Parser(lexer);
        parser.file();
        System.out.write('\n');
    }
}
