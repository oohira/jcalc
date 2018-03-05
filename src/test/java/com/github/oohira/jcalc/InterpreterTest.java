package com.github.oohira.jcalc;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class InterpreterTest {

    private Object eval(String src) {
        return new Interpreter().eval(src);
    }

    @Test
    public void number() {
        assertThat(eval("0"), is(BigDecimal.ZERO));
        assertThat(eval("1"), is(BigDecimal.ONE));
        assertThat(eval("1.0"), is(new BigDecimal("1.0")));
        assertThat(eval("10"), is(BigDecimal.TEN));
        assertThat(eval("3.14159"), is(new BigDecimal("3.14159")));
    }

    @Test
    public void string() {
        assertThat(eval("\"Hello\""), is("Hello"));
        assertThat(eval("\"Hello, World!\""), is("Hello, World!"));
    }

    @Test
    public void arithmeticOperations() {
        assertThat(eval("0.2 + 0.1"), is(new BigDecimal("0.3")));
        assertThat(eval("0.2 - 0.1"), is(new BigDecimal("0.1")));
        assertThat(eval("0.2 * 0.1"), is(new BigDecimal("0.02")));
        assertThat(eval("0.2 / 0.1"), is(new BigDecimal("2")));
        assertThat(eval("1 + 2 * 3"), is(new BigDecimal("7")));

        assertThat(eval("10 + 2 - 5"), is(new BigDecimal("7")));
        assertThat(eval("10 * 2 / 5"), is(new BigDecimal("4")));
        assertThat(eval("10 + 2 * 5 + 3"), is(new BigDecimal("23")));
        assertThat(eval("(10 + 2) * (5 + 3)"), is(new BigDecimal("96")));
    }

    @Ignore
    @Test
    public void relationalOperations() {
        assertThat(eval("1 == 2"), is(false));
        assertThat(eval("1 != 2"), is(true));
        assertThat(eval("1 <  1"), is(false));
        assertThat(eval("1 <= 1"), is(true));
        assertThat(eval("1 >  1"), is(false));
        assertThat(eval("1 >= 1"), is(true));
    }
}
