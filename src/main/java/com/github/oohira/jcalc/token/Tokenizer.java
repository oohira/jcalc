package com.github.oohira.jcalc.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.github.oohira.jcalc.token.TokenType.*;

/**
 * 字句解析器.
 *
 * ソースコードを解析してトークン列を生成する.
 */
public class Tokenizer implements Iterator<Token>, Iterable<Token> {

    /** 終端記号. */
    private static final char EOF = '\u0000';

    private final List<Token> tokens;

    private int current = 0;

    /**
     * 字句解析器を生成する.
     * TODO: 字句解析機をきちんと実装するまでテスト用で使うコンストラクタ.
     *
     * @param src ソースコード.
     */
    public Tokenizer(final String src) {
        this.tokens = parseTokens(src + EOF);
    }

    private List<Token> parseTokens(final String src) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < src.length() - 1; i++) {
            char c = src.charAt(i);
            if (isWhitespace(c)) {
                continue;
            } else if (c == '+') {
                tokens.add(new Token(OP_PLUS, "+"));
            } else if (c == '-') {
                tokens.add(new Token(OP_MINUS, "-"));
            } else if (c == '*') {
                tokens.add(new Token(OP_MULTI, "*"));
            } else if (c == '/') {
                tokens.add(new Token(OP_DIV, "/"));
            } else if (c == '=') {
                if (src.charAt(i + 1) == '=') {
                    tokens.add(new Token(OP_EQUAL, "=="));
                    i++;
                }
            } else if (c == '!') {
                if (src.charAt(i + 1) == '=') {
                    tokens.add(new Token(OP_NOT_EQUAL, "!="));
                    i++;
                }
            } else if (c == '<') {
                if (src.charAt(i + 1) == '=') {
                    tokens.add(new Token(OP_LESS_EQUAL, "<="));
                    i++;
                } else {
                    tokens.add(new Token(OP_LESS, "<"));
                }
            } else if (c == '>') {
                if (src.charAt(i + 1) == '=') {
                    tokens.add(new Token(OP_GREATER_EQUAL, ">="));
                    i++;
                } else {
                    tokens.add(new Token(OP_GREATER, ">"));
                }
            } else if (c == '(') {
                tokens.add(new Token(PAREN_LEFT, "("));
            } else if (c == ')') {
                tokens.add(new Token(PAREN_RIGHT, ")"));
            } else if (c == '"') {
                int start = i;
                boolean done = false;
                while (++i < src.length()) {
                    if (src.charAt(i) == '"') {
                        done = true;
                        break;
                    }
                }
                if (done) {
                    tokens.add(new Token(STRING, src.substring(start + 1, i)));
                    break;
                } else {
                    throw new IllegalStateException("syntax error");
                }
            } else if (isDigit(c)) {
                int start = i;
                while (isDigit(src.charAt(i + 1))) {
                    i++;
                }
                tokens.add(new Token(NUMBER, src.substring(start, i + 1)));
            } else if (isLetter(c)) {
                int start = i;
                while (isLetterOrDigit(src.charAt(i + 1))) {
                    i++;
                }
                System.out.println(src.substring(start, i + 1));
                throw new UnsupportedOperationException("FIXME: identifier");
            }
        }
        return tokens;
    }

    private boolean isLetterOrDigit(final char c) {
        return isLetter(c) || isDigit(c);
    }

    private boolean isLetter(final char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    private boolean isDigit(final char c) {
        return '0' <= c && c <= '9';
    }

    private boolean isWhitespace(final char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    @Override
    public Iterator<Token> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.current < this.tokens.size();
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.tokens.get(this.current++);
    }

    /**
     * 先頭の要素を取得する（キューから取り出さない）.
     *
     * @return 要素.
     * @throws NoSuchElementException 要素がない場合
     */
    public Token peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.tokens.get(this.current);
    }
}
