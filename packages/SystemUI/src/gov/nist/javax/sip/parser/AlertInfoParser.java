package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.AlertInfoList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AlertInfoParser extends ParametersParser {
    public AlertInfoParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        AlertInfoList alertInfoList = new AlertInfoList();
        headerName(2061);
        while (this.lexer.lookAhead(0) != '\n') {
            AlertInfo alertInfo = new AlertInfo();
            alertInfo.setHeaderName("Alert-Info");
            while (true) {
                this.lexer.SPorHT();
                if (this.lexer.lookAhead(0) == '<') {
                    this.lexer.match(60);
                    alertInfo.setAlertInfo(new URLParser((Lexer) this.lexer).uriReference(true));
                    this.lexer.match(62);
                } else {
                    alertInfo.setAlertInfo(this.lexer.byteStringNoSemicolon());
                }
                this.lexer.SPorHT();
                parse(alertInfo);
                alertInfoList.add((SIPHeader) alertInfo);
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                }
            }
        }
        return alertInfoList;
    }

    public AlertInfoParser(Lexer lexer) {
        super(lexer);
    }
}
