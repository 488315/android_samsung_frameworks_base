package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.To;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ToParser extends AddressParametersParser {
    public ToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
