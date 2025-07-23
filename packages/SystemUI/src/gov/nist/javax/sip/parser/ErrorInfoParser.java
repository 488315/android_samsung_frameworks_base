package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.ErrorInfoList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ErrorInfoParser extends ParametersParser {
    public ErrorInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ErrorInfoList errorInfoList = new ErrorInfoList();
        headerName(2058);
        while (this.lexer.lookAhead(0) != '\n') {
            while (true) {
                ErrorInfo errorInfo = new ErrorInfo();
                errorInfo.setHeaderName("Error-Info");
                this.lexer.SPorHT();
                this.lexer.match(60);
                errorInfo.setErrorInfo(new URLParser((Lexer) this.lexer).uriReference(true));
                this.lexer.match(62);
                this.lexer.SPorHT();
                parse(errorInfo);
                errorInfoList.add((SIPHeader) errorInfo);
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                }
            }
        }
        return errorInfoList;
    }

    public ErrorInfoParser(Lexer lexer) {
        super(lexer);
    }
}
