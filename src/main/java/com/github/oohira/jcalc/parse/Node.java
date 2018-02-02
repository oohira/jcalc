package com.github.oohira.jcalc.parse;

/**
 * 構文木のノードを表す抽象クラス.
 */
public abstract class Node {

    /**
     * ノードの値を評価する.
     *
     * @return ノードを評価した値.
     */
    public abstract Object eval();
}
