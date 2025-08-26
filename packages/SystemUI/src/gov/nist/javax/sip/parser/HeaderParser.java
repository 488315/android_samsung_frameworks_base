package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class HeaderParser extends Parser {
    public HeaderParser(String str) {
        this.lexer = new Lexer("command_keywordLexer", str);
    }

    public final void headerName(int i) throws ParseException {
        this.lexer.match(i);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
    }

    public SIPHeader parse() throws ParseException {
        int i;
        String str;
        String nextToken = this.lexer.getNextToken(':');
        this.lexer.consume(1);
        LexerCore lexerCore = this.lexer;
        int i2 = lexerCore.ptr;
        while (true) {
            int i3 = lexerCore.ptr;
            i = lexerCore.bufferLen;
            str = lexerCore.buffer;
            if (i3 >= i || str.charAt(i3) == '\n') {
                break;
            }
            lexerCore.ptr++;
        }
        int i4 = lexerCore.ptr;
        if (i4 < i && str.charAt(i4) == '\n') {
            lexerCore.ptr++;
        }
        String strTrim = str.substring(i2, lexerCore.ptr).trim();
        ExtensionHeaderImpl extensionHeaderImpl = new ExtensionHeaderImpl(nextToken);
        extensionHeaderImpl.setValue(strTrim);
        return extensionHeaderImpl;
    }

    public HeaderParser(Lexer lexer) {
        this.lexer = lexer;
        lexer.selectLexer("command_keywordLexer");
    }
}
