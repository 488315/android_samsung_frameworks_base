package gov.nist.javax.sip.parser;

import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import gov.nist.core.Token;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Parser extends ParserCore {
    public final ParseException createParseException(String str) {
        return new ParseException(FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder(), this.lexer.buffer, ":", str), this.lexer.ptr);
    }

    public final String method() {
        Token token = this.lexer.peekNextToken(1)[0];
        int i = token.tokenType;
        if (i != 2053 && i != 2054 && i != 2056 && i != 2055 && i != 2052 && i != 2057 && i != 2101 && i != 2102 && i != 2115 && i != 2118 && i != 4095) {
            throw createParseException("Invalid Method");
        }
        LexerCore lexerCore = this.lexer;
        lexerCore.ptr = lexerCore.savedPtr;
        return token.tokenValue;
    }
}
