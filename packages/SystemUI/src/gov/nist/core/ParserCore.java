package gov.nist.core;

import java.text.ParseException;

/* loaded from: classes4.dex */
public abstract class ParserCore {
    public static int nesting_level;
    public LexerCore lexer;

    public static void dbg_enter() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            int i2 = nesting_level;
            if (i >= i2) {
                nesting_level = i2 + 1;
                return;
            } else {
                stringBuffer.append(">");
                i++;
            }
        }
    }

    public static void dbg_leave() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            int i2 = nesting_level;
            if (i >= i2) {
                nesting_level = i2 - 1;
                return;
            } else {
                stringBuffer.append("<");
                i++;
            }
        }
    }

    public final NameValue nameValue() throws ParseException {
        boolean z;
        this.lexer.match(4095);
        LexerCore lexerCore = this.lexer;
        Token token = lexerCore.currentMatch;
        lexerCore.SPorHT();
        try {
            String strQuotedString = "";
            boolean z2 = true;
            if (this.lexer.lookAhead(0) != '=') {
                return new NameValue(token.tokenValue, "", true);
            }
            this.lexer.consume(1);
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == '\"') {
                strQuotedString = this.lexer.quotedString();
                z = true;
                z2 = false;
            } else {
                this.lexer.match(4095);
                String str = this.lexer.currentMatch.tokenValue;
                if (str == null) {
                    z = false;
                } else {
                    strQuotedString = str;
                    z = false;
                    z2 = false;
                }
            }
            NameValue nameValue = new NameValue(token.tokenValue, strQuotedString, z2);
            if (z) {
                nameValue.setQuotedValue();
            }
            return nameValue;
        } catch (ParseException unused) {
            return new NameValue(token.tokenValue, null, false);
        }
    }
}
