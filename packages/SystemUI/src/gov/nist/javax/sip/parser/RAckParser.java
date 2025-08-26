package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RAck;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class RAckParser extends HeaderParser {
    public RAckParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
