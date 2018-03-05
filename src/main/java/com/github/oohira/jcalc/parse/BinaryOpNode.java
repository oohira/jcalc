package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.TokenType;

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

    @Override
    public <T> T accept(final NodeVisitor<T> visitor) {
        return visitor.visitBinaryOpNode(this);
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
}
