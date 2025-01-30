package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RAck;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RAckParser extends HeaderParser {
    public RAckParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        RAck rAck = new RAck();
        headerName(2109);
        rAck.setHeaderName("RAck");
        try {
            rAck.setRSequenceNumber(Long.parseLong(this.lexer.number()));
            this.lexer.SPorHT();
            rAck.setCSequenceNumber(Long.parseLong(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(4095);
            rAck.setMethod(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            this.lexer.match(10);
            return rAck;
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public RAckParser(Lexer lexer) {
        super(lexer);
    }
}
