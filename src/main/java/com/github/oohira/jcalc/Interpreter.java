package com.github.oohira.jcalc;

import com.github.oohira.jcalc.parse.BinaryOpNode;
import com.github.oohira.jcalc.parse.NodeVisitor;
import com.github.oohira.jcalc.parse.NumberNode;
import com.github.oohira.jcalc.parse.Parser;
import com.github.oohira.jcalc.parse.StringNode;
import com.github.oohira.jcalc.token.Tokenizer;

import java.math.BigDecimal;

/**
 * インタープリタ.
 *
 * 構文木を評価しながらプログラムを実行する.
 */
public class Interpreter implements NodeVisitor<Object> {

    /**
     * プログラムを評価する.
     *
     * @param src プログラムコード.
     * @return ノードを評価した値.
     */
    public Object eval(String src) {
        Tokenizer tokenizer = new Tokenizer(src);
        Parser parser = new Parser(tokenizer);
        return parser.parse().accept(this);
    }

    @Override
    public Object visitBinaryOpNode(BinaryOpNode n) {
        BigDecimal lhs = (BigDecimal) n.getLeftOperand().accept(this);
        BigDecimal rhs = (BigDecimal) n.getRightOperand().accept(this);

        switch (n.getOperator()) {
            case OP_PLUS:
                return lhs.add(rhs);
            case OP_MINUS:
                return lhs.subtract(rhs);
            case OP_MULTI:
                return lhs.multiply(rhs);
            case OP_DIV:
                return lhs.divide(rhs);
            case OP_EQUAL:
                return lhs.equals(rhs);
            case OP_NOT_EQUAL:
                return !lhs.equals(rhs);
            case OP_LESS:
                return lhs.compareTo(rhs) < 0;
            case OP_LESS_EQUAL:
                return lhs.compareTo(rhs) <= 0;
            case OP_GREATER:
                return lhs.compareTo(rhs) > 0;
            case OP_GREATER_EQUAL:
                return lhs.compareTo(rhs) >= 0;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public Object visitNumberNode(NumberNode n) {
        return n.getValue();
    }

    @Override
    public Object visitStringNode(StringNode n) {
        return n.getValue();
    }
}
