import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("$ ");
        while(in.hasNext()) {
            String s = in.nextLine();
            ANTLRInputStream input = new ANTLRInputStream(s);
            CalcLexer lexer = new CalcLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CalcParser parser = new CalcParser(tokens);
            CalcParser.ProgContext tree = parser.prog(); // root of grammar
            Double result = new CalcExprVisitor().visitProg(tree);
            System.out.println("=> " + result);
            System.out.print("$ ");
        }
    }
}