package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.WWWAuthenticate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WWWAuthenticateParser extends ChallengeParser {
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
