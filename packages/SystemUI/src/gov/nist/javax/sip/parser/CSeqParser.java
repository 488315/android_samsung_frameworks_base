package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPRequest;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CSeqParser extends HeaderParser {
    public CSeqParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        try {
            CSeq cSeq = new CSeq();
            this.lexer.match(2094);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            cSeq.setSeqNumber(Long.parseLong(this.lexer.number()));
            this.lexer.SPorHT();
            cSeq.setMethod(SIPRequest.getCannonicalName(method()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return cSeq;
        } catch (NumberFormatException unused) {
            throw createParseException("Number format exception");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public CSeqParser(Lexer lexer) {
        super(lexer);
    }
}
