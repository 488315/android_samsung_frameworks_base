package gov.nist.javax.sip.parser;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AuthorizationParser extends ChallengeParser {
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
