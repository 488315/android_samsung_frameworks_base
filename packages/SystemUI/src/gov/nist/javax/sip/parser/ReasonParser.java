package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.ReasonList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReasonParser extends ParametersParser {
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
