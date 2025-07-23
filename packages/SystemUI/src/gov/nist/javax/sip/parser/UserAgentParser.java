package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.UserAgent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UserAgentParser extends HeaderParser {
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
