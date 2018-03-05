package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;

/**
 * 文字列を表すノード.
 */
public class StringNode extends Node {

    private final String value;

    StringNode(final Token token) {
        this.value = token.getText();
    }

    @Override
    public <T> T accept(final NodeVisitor<T> visitor) {
        return visitor.visitStringNode(this);
    }

    /**
     * 値を取得する.
     *
     * @return 値.
     */
    public String getValue() {
        return this.value;
    }
}
