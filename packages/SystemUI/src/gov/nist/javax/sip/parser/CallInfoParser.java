package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CallInfo;
import gov.nist.javax.sip.header.CallInfoList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CallInfoParser extends ParametersParser {
    public CallInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        CallInfoList callInfoList = new CallInfoList();
        headerName(2099);
        while (this.lexer.lookAhead(0) != '\n') {
            CallInfo callInfo = new CallInfo();
            callInfo.setHeaderName("Call-Info");
            this.lexer.SPorHT();
            this.lexer.match(60);
            callInfo.setInfo(new URLParser((Lexer) this.lexer).uriReference(true));
            this.lexer.match(62);
            this.lexer.SPorHT();
            parse(callInfo);
            callInfoList.add((SIPHeader) callInfo);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                CallInfo callInfo2 = new CallInfo();
                this.lexer.SPorHT();
                this.lexer.match(60);
                callInfo2.setInfo(new URLParser((Lexer) this.lexer).uriReference(true));
                this.lexer.match(62);
                this.lexer.SPorHT();
                parse(callInfo2);
                callInfoList.add((SIPHeader) callInfo2);
            }
        }
        return callInfoList;
    }

    public CallInfoParser(Lexer lexer) {
        super(lexer);
    }
}
