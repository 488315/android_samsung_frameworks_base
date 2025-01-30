package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CallInfo;
import gov.nist.javax.sip.header.CallInfoList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CallInfoParser extends ParametersParser {
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
