package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class MinExpiresParser extends HeaderParser {
    public MinExpiresParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
