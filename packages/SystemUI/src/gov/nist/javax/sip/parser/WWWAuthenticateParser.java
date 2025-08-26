package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.WWWAuthenticate;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class WWWAuthenticateParser extends ChallengeParser {
    public WWWAuthenticateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2096);
        WWWAuthenticate wWWAuthenticate = new WWWAuthenticate();
        parse(wWWAuthenticate);
        return wWWAuthenticate;
    }

    public WWWAuthenticateParser(Lexer lexer) {
        super(lexer);
    }
}
