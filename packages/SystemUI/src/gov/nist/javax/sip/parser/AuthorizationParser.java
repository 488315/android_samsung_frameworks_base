package gov.nist.javax.sip.parser;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.SIPHeader;

/* loaded from: classes4.dex */
public class AuthorizationParser extends ChallengeParser {
    public AuthorizationParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ParserCore.dbg_enter();
        try {
            headerName(2071);
            Authorization authorization = new Authorization();
            parse(authorization);
            return authorization;
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public AuthorizationParser(Lexer lexer) {
        super(lexer);
    }
}
