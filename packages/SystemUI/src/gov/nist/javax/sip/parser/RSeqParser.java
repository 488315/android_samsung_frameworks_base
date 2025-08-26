package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class RSeqParser extends HeaderParser {
    public RSeqParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
