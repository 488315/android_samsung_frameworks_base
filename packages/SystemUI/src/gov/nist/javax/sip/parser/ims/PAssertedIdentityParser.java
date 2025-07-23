package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssertedIdentityList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PAssertedIdentityParser extends AddressParametersParser {
    public PAssertedIdentityParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        PAssertedIdentityList pAssertedIdentityList = new PAssertedIdentityList();
        headerName(2121);
        PAssertedIdentity pAssertedIdentity = new PAssertedIdentity();
        pAssertedIdentity.setHeaderName("P-Asserted-Identity");
        parse((AddressParametersHeader) pAssertedIdentity);
        pAssertedIdentityList.add((SIPHeader) pAssertedIdentity);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            PAssertedIdentity pAssertedIdentity2 = new PAssertedIdentity();
            parse((AddressParametersHeader) pAssertedIdentity2);
            pAssertedIdentityList.add((SIPHeader) pAssertedIdentity2);
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        this.lexer.match(10);
        return pAssertedIdentityList;
    }

    public PAssertedIdentityParser(Lexer lexer) {
        super(lexer);
    }
}
