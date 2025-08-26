package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ProxyAuthorizationParser extends ChallengeParser {
    public ProxyAuthorizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2072);
        ProxyAuthorization proxyAuthorization = new ProxyAuthorization();
        parse(proxyAuthorization);
        return proxyAuthorization;
    }

    public ProxyAuthorizationParser(Lexer lexer) {
        super(lexer);
    }
}
