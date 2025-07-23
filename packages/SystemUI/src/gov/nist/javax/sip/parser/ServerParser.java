package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Server;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ServerParser extends HeaderParser {
    public ServerParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        LexerCore lexerCore;
        int i;
        Server server = new Server();
        headerName(2066);
        int i2 = 0;
        if (this.lexer.lookAhead(0) == '\n') {
            throw createParseException("empty header");
        }
        while (this.lexer.lookAhead(0) != '\n' && this.lexer.lookAhead(0) != 0) {
            if (this.lexer.lookAhead(0) == '(') {
                server.addProductToken("(" + this.lexer.comment() + ')');
            } else {
                try {
                    lexerCore = this.lexer;
                    i = lexerCore.ptr;
                } catch (ParseException unused) {
                }
                try {
                    String string = lexerCore.getString();
                    if (string.charAt(string.length() - 1) == '\n') {
                        string = string.trim();
                    }
                    server.addProductToken(string);
                } catch (ParseException unused2) {
                    i2 = i;
                    LexerCore lexerCore2 = this.lexer;
                    lexerCore2.ptr = i2;
                    server.addProductToken(lexerCore2.getRest().trim());
                    return server;
                }
            }
        }
        return server;
    }

    public ServerParser(Lexer lexer) {
        super(lexer);
    }
}
