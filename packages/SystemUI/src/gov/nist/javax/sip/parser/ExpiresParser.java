package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExpiresParser extends HeaderParser {
    public ExpiresParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        Expires expires = new Expires();
        this.lexer.match(2090);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        String ttoken = this.lexer.ttoken();
        this.lexer.match(10);
        try {
            expires.setExpires(Integer.parseInt(ttoken));
            return expires;
        } catch (NumberFormatException unused) {
            throw createParseException("bad integer format");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public ExpiresParser(Lexer lexer) {
        super(lexer);
    }
}
