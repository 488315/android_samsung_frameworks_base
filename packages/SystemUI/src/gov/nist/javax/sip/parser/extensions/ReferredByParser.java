package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.ReferredBy;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ReferredByParser extends AddressParametersParser {
    public ReferredByParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
