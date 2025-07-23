package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ProxyAuthorizationParser extends ChallengeParser {
    public ProxyAuthorizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2072);
        ProxyAuthorization proxyAuthorization = new ProxyAuthorization();
        parse(proxyAuthorization);
        return proxyAuthorization;
    }

    public ProxyAuthorizationParser(Lexer lexer) {
        super(lexer);
    }
}
