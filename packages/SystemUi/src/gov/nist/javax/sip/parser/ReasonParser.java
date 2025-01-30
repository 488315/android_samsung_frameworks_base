package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.ReasonList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReasonParser extends ParametersParser {
    public ReasonParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ReasonList reasonList = new ReasonList();
        headerName(2107);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) != '\n') {
            Reason reason = new Reason();
            this.lexer.match(4095);
            reason.setProtocol(this.lexer.currentMatch.tokenValue);
            parse(reason);
            reasonList.add((SIPHeader) reason);
            if (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
            } else {
                this.lexer.SPorHT();
            }
        }
        return reasonList;
    }

    public ReasonParser(Lexer lexer) {
        super(lexer);
    }
}
