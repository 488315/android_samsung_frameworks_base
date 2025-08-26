package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPIfMatch;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class SIPIfMatchParser extends HeaderParser {
    public SIPIfMatchParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
