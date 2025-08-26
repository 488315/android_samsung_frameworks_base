package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReferTo;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ReferToParser extends AddressParametersParser {
    public ReferToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2114);
        ReferTo referTo = new ReferTo();
        parse((AddressParametersHeader) referTo);
        this.lexer.match(10);
        return referTo;
    }

    public ReferToParser(Lexer lexer) {
        super(lexer);
    }
}
