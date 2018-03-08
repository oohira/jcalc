package com.github.oohira.jcalc.token;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link TokenType} のテストクラス.
 */
class TokenTypeTest {

    @Test
    void isRelationalOperator() {
        assertFalse(TokenType.OP_PLUS.isRelationalOperator());
        assertFalse(TokenType.OP_MINUS.isRelationalOperator());
        assertFalse(TokenType.OP_MULTI.isRelationalOperator());
        assertFalse(TokenType.OP_DIV.isRelationalOperator());

        assertTrue(TokenType.OP_EQUAL.isRelationalOperator());
        assertTrue(TokenType.OP_NOT_EQUAL.isRelationalOperator());
        assertTrue(TokenType.OP_LESS.isRelationalOperator());
        assertTrue(TokenType.OP_LESS_EQUAL.isRelationalOperator());
        assertTrue(TokenType.OP_GREATER.isRelationalOperator());
        assertTrue(TokenType.OP_GREATER_EQUAL.isRelationalOperator());
    }
}