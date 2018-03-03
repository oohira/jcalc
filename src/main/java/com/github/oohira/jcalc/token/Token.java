package com.github.oohira.jcalc.token;

import java.util.Objects;

/**
 * トークン.
 */
public class Token {

    private final TokenType type;
    private final String text;

    public Token(final TokenType type, final String text) {
        this.type = type;
        this.text = text;
    }

    /**
     * トークンの種類を取得する.
     *
     * @return トークンの種類.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * トークンのソーステキストを取得する.
     *
     * @return ソーステキスト.
     */
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        Token token = (Token) o;
        return Objects.equals(type, token.type)
                && Objects.equals(text, token.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }

    @Override
    public String toString() {
        return "Token{" + type + ", " + text + "}";
    }
}
