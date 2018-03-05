package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;

import java.math.BigDecimal;

/**
 * 数値を表すノード.
 */
public class NumberNode extends Node {

    private final BigDecimal value;

    NumberNode(final Token token) {
        this.value = new BigDecimal(token.getText());
    }

    @Override
    public <T> T accept(final NodeVisitor<T> visitor) {
        return visitor.visitNumberNode(this);
    }

    /**
     * 値を取得する.
     *
     * @return 値.
     */
    public BigDecimal getValue() {
        return this.value;
    }
}
