package gov.nist.javax.sip.parser;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
