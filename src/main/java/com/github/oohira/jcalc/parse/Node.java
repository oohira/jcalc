package com.github.oohira.jcalc.parse;

/**
 * 構文木のノードを表す抽象クラス.
 */
public abstract class Node {

    /**
     * Visitorを使って構文木を走査する.
     *
     * 走査順を制御するのはVisitorの責任である。Visitorが子ノードのaccept()メソッドを
     * 実行しなければ、処理が子ノードや兄弟ノードへ進むことはない。
     *
     * @param visitor 各ノードを走査するVisitor.
     * @param <T> 戻り値の型.
     * @return 処理結果.
     */
    public abstract <T> T accept(NodeVisitor<T> visitor);
}
