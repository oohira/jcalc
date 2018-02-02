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
    public Object eval() {
        return this.value;
    }
}
