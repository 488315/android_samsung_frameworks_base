package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RSeqParser extends HeaderParser {
    public RSeqParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        RSeq rSeq = new RSeq();
        headerName(2108);
        rSeq.setHeaderName("RSeq");
        try {
            rSeq.setSeqNumber(Long.parseLong(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return rSeq;
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public RSeqParser(Lexer lexer) {
        super(lexer);
    }
}
