package com.github.oohira.jcalc.token;

/**
 * トークンの種類.
 */
public enum TokenType {

    /** 数値 */
    NUMBER,

    /** 加算演算子 */
    OP_PLUS,

    /** 減算演算子 */
    OP_MINUS,

    /** 乗算演算子 */
    OP_MULTI,

    /** 除算演算子 */
    OP_DIV,

    /** 開き括弧 */
    PAREN_LEFT,

    /** 閉じ括弧 */
    PAREN_RIGHT,

    /** 文字列 */
    STRING,
}
