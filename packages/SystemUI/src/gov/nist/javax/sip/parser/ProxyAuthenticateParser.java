package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ProxyAuthenticateParser extends ChallengeParser {
    public ProxyAuthenticateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2082);
        ProxyAuthenticate proxyAuthenticate = new ProxyAuthenticate();
        parse(proxyAuthenticate);
        return proxyAuthenticate;
    }

    public ProxyAuthenticateParser(Lexer lexer) {
        super(lexer);
    }
}
