package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPRequest;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class CSeqParser extends HeaderParser {
    public CSeqParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
