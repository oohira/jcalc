package com.github.oohira.jcalc.token;

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
}
