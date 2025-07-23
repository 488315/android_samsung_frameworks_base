package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReferTo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReferToParser extends AddressParametersParser {
    public ReferToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
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
