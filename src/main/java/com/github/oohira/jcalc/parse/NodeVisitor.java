package com.github.oohira.jcalc.parse;

/**
 * 構文木のノードを走査するクラスが実装すべきインターフェース.
 */
public interface NodeVisitor<T> {

    /**
     * 二項演算子ノードを処理する.
     *
     * @param n ノード.
     * @return 処理結果.
     */
    T visitBinaryOpNode(BinaryOpNode n);

    /**
     * 数値ノードを処理する.
     *
     * @param n ノード.
     * @return 処理結果.
     */
    T visitNumberNode(NumberNode n);

    /**
     * 文字列ノードを処理する.
     *
     * @param n ノード.
     * @return 処理結果.
     */
    T visitStringNode(StringNode n);
}
