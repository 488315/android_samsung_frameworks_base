package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class AuthenticationInfoParser extends ParametersParser {
    public AuthenticationInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
