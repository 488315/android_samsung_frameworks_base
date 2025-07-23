package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MinExpiresParser extends HeaderParser {
    public MinExpiresParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        MinExpires minExpires = new MinExpires();
        headerName(2110);
        minExpires.setHeaderName("Min-Expires");
        try {
            minExpires.setExpires(Integer.parseInt(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return minExpires;
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public MinExpiresParser(Lexer lexer) {
        super(lexer);
    }
}
