package com.github.oohira.jcalc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    private Object eval(String src) {
        return new Interpreter().eval(src);
    }

    @Test
    public void number() {
        assertEquals(BigDecimal.ZERO, eval("0"));
        assertEquals(BigDecimal.ONE, eval("1"));
        assertEquals(new BigDecimal("1.0"), eval("1.0"));
        assertEquals(BigDecimal.TEN, eval("10"));
        assertEquals(new BigDecimal("3.14159"), eval("3.14159"));
    }

    @Test
    public void string() {
        assertEquals("Hello", eval("\"Hello\""));
        assertEquals("Hello, World!", eval("\"Hello, World!\""));
    }

    @Test
    public void arithmeticOperations() {
        assertEquals(new BigDecimal("0.3"), eval("0.2 + 0.1"));
        assertEquals(new BigDecimal("0.1"), eval("0.2 - 0.1"));
        assertEquals(new BigDecimal("0.02"), eval("0.2 * 0.1"));
        assertEquals(new BigDecimal("2"), eval("0.2 / 0.1"));
        assertEquals(new BigDecimal("7"), eval("1 + 2 * 3"));

        assertEquals(new BigDecimal("7"), eval("10 + 2 - 5"));
        assertEquals(new BigDecimal("4"), eval("10 * 2 / 5"));
        assertEquals(new BigDecimal("23"), eval("10 + 2 * 5 + 3"));
        assertEquals(new BigDecimal("96"), eval("(10 + 2) * (5 + 3)"));
    }

    @Disabled
    @Test
    public void relationalOperations() {
        assertFalse((Boolean) eval("1 == 2"));
        assertTrue((Boolean) eval("1 != 2"));
        assertFalse((Boolean) eval("1 <  1"));
        assertTrue((Boolean) eval("1 <= 1"));
        assertFalse((Boolean) eval("1 >  1"));
        assertTrue((Boolean) eval("1 >= 1"));
    }
}
