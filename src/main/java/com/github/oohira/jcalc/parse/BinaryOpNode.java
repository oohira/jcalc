package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.TokenType;

import java.math.BigDecimal;

/**
 * 二項演算子を表すノード.
 */
public class BinaryOpNode extends Node {

    private final TokenType op;
    private final Node left;
    private final Node right;

    BinaryOpNode(final TokenType op, final Node left, final Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    /**
     * 演算子を取得する.
     *
     * @return 演算子.
     */
    public TokenType getOperator() {
        return this.op;
    }

    /**
     * 二項演算の左辺を取得する.
     *
     * @return 左辺ノード.
     */
    public Node getLeftOperand() {
        return this.left;
    }

    /**
     * 二項演算の右辺を取得する.
     *
     * @return 右辺ノード.
     */
    public Node getRightOperand() {
        return this.right;
    }

    @Override
    public Object eval() {
        BigDecimal lhs = (BigDecimal) this.left.eval();
        BigDecimal rhs = (BigDecimal) this.right.eval();

        switch (this.op) {
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
}
