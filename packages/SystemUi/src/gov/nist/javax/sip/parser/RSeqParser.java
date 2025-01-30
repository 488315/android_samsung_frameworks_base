package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RSeqParser extends HeaderParser {
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
