import java.util.HashMap;
import java.util.Map;


public class CalcExprVisitor extends CalcBaseListener
        implements CalcListener {

    static Map<String, Double> variables = new HashMap<>();

    public Double visitProg(CalcParser.ProgContext ctx) {
        if (ctx.setup() != null) {
            return this.visitSetup(ctx);
        } else {
            return this.visitExpr(ctx.expr());
        }
    }

    public Double visitSetup(CalcParser.ProgContext ctx) {
        String v = ctx.setup().left.getText();
        variables.put(v, this.visitExpr(ctx.setup().right));
        return variables.get(v);
    }

    public Double visitExpr(CalcParser.ExprContext ctx) {
        Double result = 0.0;

        if(ctx.neg != null) {
            result = -this.visitExpr(ctx.expr().get(0));
        }

        else if(ctx.op != null) {
            if(ctx.right == null) {
                Double l = this.visitExpr(ctx.left);
                switch(ctx.op.getText()) {
                    case "%": result = l*0.01; break;
                }
            } else {
                Double l = this.visitExpr(ctx.left);
                Double r = this.visitExpr(ctx.right);

                switch(ctx.op.getText()) {
                    case "+": result = l+r; break;
                    case "-": result = l-r; break;
                    case "*": result = l*r; break;
                    case "/": result = l/r; break;
                    case "^": result = Math.pow(l,r); break;
                }
            }

        } else if(ctx.funcCall() != null) {
            result = this.visitFuncCall(ctx.funcCall());

        } else if(ctx.NUMBER() != null) {
            switch(ctx.NUMBER().getText()) {
                case "PI": result = Math.PI; break;
                case "E": result = Math.E; break;
                default: result = Double.parseDouble(ctx.NUMBER().getText());
            }

        } else if(ctx.VARIABLE() != null) {
            if(!variables.containsKey(ctx.VARIABLE().getText())) {
                variables.put(ctx.VARIABLE().getText(), 0d);
                result = 0d;
            } else {
                result = variables.get(ctx.VARIABLE().getText());
            }
        } else {
            result = this.visitExpr(ctx.expr().get(0));
        }

        return result;
    }

    public Double visitFuncCall(CalcParser.FuncCallContext ctx) {
        Double result = 0.0;
        Double expr = this.visitExpr(ctx.expr());

        switch(ctx.f.getText()) {
            case "sin": result = Math.sin(expr); break;
            case "cos": result = Math.cos(expr); break;
            case "log": result = Math.log(expr); break;
        }

        return result;
    }
}