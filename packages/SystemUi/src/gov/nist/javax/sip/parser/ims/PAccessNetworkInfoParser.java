package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfo;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PAccessNetworkInfoParser extends HeaderParser {
    public PAccessNetworkInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2127);
        PAccessNetworkInfo pAccessNetworkInfo = new PAccessNetworkInfo();
        pAccessNetworkInfo.setHeaderName("P-Access-Network-Info");
        this.lexer.SPorHT();
        this.lexer.match(4095);
        pAccessNetworkInfo.setAccessType(this.lexer.currentMatch.tokenValue);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ';') {
            this.lexer.match(59);
            this.lexer.SPorHT();
            pAccessNetworkInfo.setParameter(nameValue());
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        this.lexer.match(10);
        return pAccessNetworkInfo;
    }

    public PAccessNetworkInfoParser(Lexer lexer) {
        super(lexer);
    }
}
