package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PMediaAuthorizationParser extends HeaderParser {
    public PMediaAuthorizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        PMediaAuthorizationList pMediaAuthorizationList = new PMediaAuthorizationList();
        headerName(2130);
        PMediaAuthorization pMediaAuthorization = new PMediaAuthorization();
        pMediaAuthorization.setHeaderName("P-Media-Authorization");
        while (this.lexer.lookAhead(0) != '\n') {
            this.lexer.match(4095);
            try {
                pMediaAuthorization.setMediaAuthorizationToken(this.lexer.currentMatch.tokenValue);
                pMediaAuthorizationList.add((SIPHeader) pMediaAuthorization);
                this.lexer.SPorHT();
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    pMediaAuthorization = new PMediaAuthorization();
                }
                this.lexer.SPorHT();
            } catch (InvalidArgumentException e) {
                throw createParseException(e.getMessage());
            }
        }
        return pMediaAuthorizationList;
    }

    public PMediaAuthorizationParser(Lexer lexer) {
        super(lexer);
    }
}
