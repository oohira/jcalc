package com.github.oohira.jcalc.token;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 字句解析器.
 *
 * ソースコードを解析してトークン列を生成する.
 */
public class Tokenizer implements Iterator<Token>, Iterable<Token> {

    private final Token[] tokens;

    private int current = 0;

    /**
     * TODO: 字句解析機を実装するまでテスト用で使うコンストラクタ
     *
     * @param tokens トークン列
     */
    public Tokenizer(final Token... tokens) {
        this.tokens = tokens;
    }

    @Override
    public Iterator<Token> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.current < this.tokens.length;
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.tokens[this.current++];
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
        return this.tokens[this.current];
    }
}
