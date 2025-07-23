package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReplyToParser extends AddressParametersParser {
    public ReplyToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
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
