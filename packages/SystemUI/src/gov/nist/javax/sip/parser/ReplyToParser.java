package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ReplyToParser extends AddressParametersParser {
    public ReplyToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        ReplyTo replyTo = new ReplyTo();
        headerName(2106);
        replyTo.setHeaderName("Reply-To");
        parse((AddressParametersHeader) replyTo);
        return replyTo;
    }

    public ReplyToParser(Lexer lexer) {
        super(lexer);
    }
}
