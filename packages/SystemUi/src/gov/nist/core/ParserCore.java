package gov.nist.core;

import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    public final NameValue nameValue() {
        boolean z;
        this.lexer.match(4095);
        LexerCore lexerCore = this.lexer;
        Token token = lexerCore.currentMatch;
        lexerCore.SPorHT();
        try {
            String str = "";
            boolean z2 = true;
            if (this.lexer.lookAhead(0) != '=') {
                return new NameValue(token.tokenValue, "", true);
            }
            this.lexer.consume(1);
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == '\"') {
                str = this.lexer.quotedString();
                z = true;
                z2 = false;
            } else {
                this.lexer.match(4095);
                String str2 = this.lexer.currentMatch.tokenValue;
                if (str2 == null) {
                    z = false;
                } else {
                    str = str2;
                    z = false;
                    z2 = false;
                }
            }
            NameValue nameValue = new NameValue(token.tokenValue, str, z2);
            if (z) {
                nameValue.setQuotedValue();
            }
            return nameValue;
        } catch (ParseException unused) {
            return new NameValue(token.tokenValue, null, false);
        }
    }
}
