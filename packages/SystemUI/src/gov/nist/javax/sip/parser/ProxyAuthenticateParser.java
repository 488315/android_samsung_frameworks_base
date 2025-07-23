package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ProxyAuthenticateParser extends ChallengeParser {
    public ProxyAuthenticateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2082);
        ProxyAuthenticate proxyAuthenticate = new ProxyAuthenticate();
        parse(proxyAuthenticate);
        return proxyAuthenticate;
    }

    public ProxyAuthenticateParser(Lexer lexer) {
        super(lexer);
    }
}
