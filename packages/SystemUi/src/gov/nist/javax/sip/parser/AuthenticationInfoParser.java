package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AuthenticationInfoParser extends ParametersParser {
    public AuthenticationInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2112);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setHeaderName("Authentication-Info");
        this.lexer.SPorHT();
        authenticationInfo.setParameter(nameValue());
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            authenticationInfo.setParameter(nameValue());
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        return authenticationInfo;
    }

    public AuthenticationInfoParser(Lexer lexer) {
        super(lexer);
    }
}
