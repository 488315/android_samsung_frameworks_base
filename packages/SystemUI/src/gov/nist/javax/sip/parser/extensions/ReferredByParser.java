package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.ReferredBy;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReferredByParser extends AddressParametersParser {
    public ReferredByParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2132);
        ReferredBy referredBy = new ReferredBy();
        parse((AddressParametersHeader) referredBy);
        this.lexer.match(10);
        return referredBy;
    }

    public ReferredByParser(Lexer lexer) {
        super(lexer);
    }
}
