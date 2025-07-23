package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SIPETagParser extends HeaderParser {
    public SIPETagParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        SIPETag sIPETag = new SIPETag();
        headerName(2116);
        this.lexer.SPorHT();
        this.lexer.match(4095);
        sIPETag.setETag(this.lexer.currentMatch.tokenValue);
        this.lexer.SPorHT();
        this.lexer.match(10);
        return sIPETag;
    }

    public SIPETagParser(Lexer lexer) {
        super(lexer);
    }
}
