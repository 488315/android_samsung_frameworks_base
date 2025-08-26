package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentity;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class PPreferredIdentityParser extends AddressParametersParser {
    public PPreferredIdentityParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        this.lexer.match(2122);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        PPreferredIdentity pPreferredIdentity = new PPreferredIdentity();
        parse((AddressParametersHeader) pPreferredIdentity);
        return pPreferredIdentity;
    }

    public PPreferredIdentityParser(Lexer lexer) {
        super(lexer);
    }
}
