package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class SIPETagParser extends HeaderParser {
    public SIPETagParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
