package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CallIDParser extends HeaderParser {
    public CallIDParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        this.lexer.match(2088);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        CallID callID = new CallID();
        this.lexer.SPorHT();
        callID.setCallId(this.lexer.getRest().trim());
        return callID;
    }

    public CallIDParser(Lexer lexer) {
        super(lexer);
    }
}
