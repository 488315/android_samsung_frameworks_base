package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.AllowList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AllowParser extends HeaderParser {
    public AllowParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        AllowList allowList = new AllowList();
        headerName(2069);
        Allow allow = new Allow();
        allow.setHeaderName("Allow");
        this.lexer.SPorHT();
        this.lexer.match(4095);
        allow.setMethod(this.lexer.currentMatch.tokenValue);
        allowList.add((SIPHeader) allow);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            Allow allow2 = new Allow();
            this.lexer.match(4095);
            allow2.setMethod(this.lexer.currentMatch.tokenValue);
            allowList.add((SIPHeader) allow2);
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        this.lexer.match(10);
        return allowList;
    }

    public AllowParser(Lexer lexer) {
        super(lexer);
    }
}
