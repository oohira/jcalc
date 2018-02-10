package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import com.github.oohira.jcalc.token.Tokenizer;

import java.util.Objects;

import static com.github.oohira.jcalc.token.TokenType.*;

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
        return parseExpression(this.tokenizer);
    }

    /**
     * 式をパースする.
     *
     * EXPRESSION := TERM { ("+"|"-") TERM }
     *
     * @return 式ノード.
     */
    Node parseExpression(final Tokenizer tokenizer) {
        Node expression = parseTerm(tokenizer);
        while (tokenizer.hasNext() &&
                (tokenizer.peek().getType() == OP_PLUS || tokenizer.peek().getType() == OP_MINUS)) {
            Token op = tokenizer.next();
            Node term = parseTerm(tokenizer);
            expression = new BinaryOpNode(op.getType(), expression, term);
        }
        return expression;
    }

    /**
     * 項をパースする.
     *
     * TERM := FACTOR { ("*"|"/") FACTOR }
     *
     * @return 項ノード.
     */
    Node parseTerm(final Tokenizer tokenizer) {
        Node term = parseFactor(tokenizer);
        while (tokenizer.hasNext() &&
                (tokenizer.peek().getType() == OP_MULTI || tokenizer.peek().getType() == OP_DIV)) {
            Token op = tokenizer.next();
            Node factor = parseFactor(tokenizer);
            term = new BinaryOpNode(op.getType(), term, factor);
        }
        return term;
    }

    /**
     * 因子をパースする.
     *
     * FACTOR := NUMBER | STRING
     *
     * @return 因子ノード.
     */
    Node parseFactor(final Tokenizer tokenizer) {
        Token token = tokenizer.next();
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
