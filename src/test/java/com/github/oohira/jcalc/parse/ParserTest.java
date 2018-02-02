package com.github.oohira.jcalc.parse;

import com.github.oohira.jcalc.token.Token;
import com.github.oohira.jcalc.token.Tokenizer;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.oohira.jcalc.token.TokenType.NUMBER;
import static com.github.oohira.jcalc.token.TokenType.STRING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * {@link Parser} のテストクラス.
 */
public class ParserTest {

    @Test
    public void parseNumber() {
        Tokenizer tokenizer = new Tokenizer(new Token(NUMBER, "1"));
        Parser parser = new Parser(tokenizer);

        NumberNode n = (NumberNode) parser.parse();
        assertThat(n.eval(), is(new BigDecimal("1")));
    }

    @Test
    public void parseString() {
        Tokenizer tokenizer = new Tokenizer(new Token(STRING, "Hello"));
        Parser parser = new Parser(tokenizer);

        StringNode n = (StringNode) parser.parse();
        assertThat(n.eval(), is("Hello"));
    }
}
