package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.WWWAuthenticate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WWWAuthenticateParser extends ChallengeParser {
    public WWWAuthenticateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2096);
        WWWAuthenticate wWWAuthenticate = new WWWAuthenticate();
        parse(wWWAuthenticate);
        return wWWAuthenticate;
    }

    public WWWAuthenticateParser(Lexer lexer) {
        super(lexer);
    }
}
