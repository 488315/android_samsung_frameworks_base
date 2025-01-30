package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SIPETagParser extends HeaderParser {
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
