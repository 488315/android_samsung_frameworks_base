package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPIfMatch;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SIPIfMatchParser extends HeaderParser {
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
