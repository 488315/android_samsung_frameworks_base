package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.To;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ToParser extends AddressParametersParser {
    public ToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2063);
        To to = new To();
        parse((AddressParametersHeader) to);
        this.lexer.match(10);
        return to;
    }

    public ToParser(Lexer lexer) {
        super(lexer);
    }
}
