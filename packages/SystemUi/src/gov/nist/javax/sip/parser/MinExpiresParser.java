package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MinExpiresParser extends HeaderParser {
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
