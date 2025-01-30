package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.UserAgent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UserAgentParser extends HeaderParser {
    public UserAgentParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        UserAgent userAgent = new UserAgent();
        headerName(2065);
        if (this.lexer.lookAhead(0) == '\n') {
            throw createParseException("empty header");
        }
        while (this.lexer.lookAhead(0) != '\n' && this.lexer.lookAhead(0) != 0) {
            if (this.lexer.lookAhead(0) == '(') {
                userAgent.addProductToken("(" + this.lexer.comment() + ')');
            } else {
                ((Lexer) this.lexer).SPorHT();
                String byteStringNoSlash = this.lexer.byteStringNoSlash();
                if (byteStringNoSlash == null) {
                    throw createParseException("Expected product string");
                }
                StringBuffer stringBuffer = new StringBuffer(byteStringNoSlash);
                if (this.lexer.peekNextToken(1)[0].tokenType == 47) {
                    this.lexer.match(47);
                    ((Lexer) this.lexer).SPorHT();
                    String byteStringNoSlash2 = this.lexer.byteStringNoSlash();
                    if (byteStringNoSlash2 == null) {
                        throw createParseException("Expected product version");
                    }
                    stringBuffer.append("/");
                    stringBuffer.append(byteStringNoSlash2);
                }
                userAgent.addProductToken(stringBuffer.toString());
            }
            this.lexer.SPorHT();
        }
        return userAgent;
    }

    public UserAgentParser(Lexer lexer) {
        super(lexer);
    }
}
