package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPRequest;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CSeqParser extends HeaderParser {
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
