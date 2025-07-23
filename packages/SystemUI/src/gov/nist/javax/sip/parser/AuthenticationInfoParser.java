package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AuthenticationInfoParser extends ParametersParser {
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
