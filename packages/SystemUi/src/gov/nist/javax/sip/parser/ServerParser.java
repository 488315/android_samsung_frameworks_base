package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Server;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ServerParser extends HeaderParser {
    public ServerParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        Server server = new Server();
        headerName(2066);
        int i = 0;
        if (this.lexer.lookAhead(0) == '\n') {
            throw createParseException("empty header");
        }
        while (this.lexer.lookAhead(0) != '\n' && this.lexer.lookAhead(0) != 0) {
            if (this.lexer.lookAhead(0) == '(') {
                server.addProductToken("(" + this.lexer.comment() + ')');
            } else {
                try {
                    LexerCore lexerCore = this.lexer;
                    int i2 = lexerCore.ptr;
                    try {
                        String string = lexerCore.getString();
                        if (string.charAt(string.length() - 1) == '\n') {
                            string = string.trim();
                        }
                        server.addProductToken(string);
                    } catch (ParseException unused) {
                        i = i2;
                        LexerCore lexerCore2 = this.lexer;
                        lexerCore2.ptr = i;
                        server.addProductToken(lexerCore2.getRest().trim());
                        return server;
                    }
                } catch (ParseException unused2) {
                }
            }
        }
        return server;
    }

    public ServerParser(Lexer lexer) {
        super(lexer);
    }
}
