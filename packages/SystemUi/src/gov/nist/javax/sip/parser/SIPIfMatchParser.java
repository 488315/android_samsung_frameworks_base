package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPIfMatch;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SIPIfMatchParser extends HeaderParser {
    public SIPIfMatchParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        SIPIfMatch sIPIfMatch = new SIPIfMatch();
        headerName(2117);
        this.lexer.SPorHT();
        this.lexer.match(4095);
        sIPIfMatch.setETag(this.lexer.currentMatch.tokenValue);
        this.lexer.SPorHT();
        this.lexer.match(10);
        return sIPIfMatch;
    }

    public SIPIfMatchParser(Lexer lexer) {
        super(lexer);
    }
}
