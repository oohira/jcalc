package com.github.oohira.jcalc.token;

/**
 * トークンの種類.
 */
public enum TokenType {

    /** 数値. */
    NUMBER,

    /** 加算演算子. */
    OP_PLUS,

    /** 減算演算子. */
    OP_MINUS,

    /** 乗算演算子. */
    OP_MULTI,

    /** 除算演算子. */
    OP_DIV,

    /** == 演算子. */
    OP_EQUAL,

    /** != 演算子. */
    OP_NOT_EQUAL,

    /** < 演算子. */
    OP_LESS,

    /** <= 演算子. */
    OP_LESS_EQUAL,

    /** > 演算子. */
    OP_GREATER,

    /** <= 演算子. */
    OP_GREATER_EQUAL,

    /** 開き括弧. */
    PAREN_LEFT,

    /** 閉じ括弧. */
    PAREN_RIGHT,

    /** 文字列. */
    STRING;

    /**
     * 比較演算子かどうか判定する.
     *
     * @return 比較演算子の場合は true.
     */
    public boolean isRelationalOperator() {
        return this == OP_EQUAL
                || this == OP_NOT_EQUAL
                || this == OP_LESS
                || this == OP_LESS_EQUAL
                || this == OP_GREATER
                || this == OP_GREATER_EQUAL;
    }
}
