package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CallIDParser extends HeaderParser {
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
