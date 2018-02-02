package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import com.github.oohira.jcalc.token.Tokenizer;

import java.util.Objects;

/**
 * 構文解析器.
 *
 * トークン列を解析して構文木を生成する.
 */
public class Parser {

    private final Tokenizer tokenizer;

    public Parser(final Tokenizer tokenizer) {
        this.tokenizer = Objects.requireNonNull(tokenizer);
    }

    /**
     * 構文木を生成する.
     *
     * @return 構文木.
     */
    public Node parse() {
        Token token = this.tokenizer.next();
        switch (token.getType()) {
            case NUMBER:
                return new NumberNode(token);
            case STRING:
                return new StringNode(token);
            default:
                throw new IllegalStateException();
        }
    }
}
