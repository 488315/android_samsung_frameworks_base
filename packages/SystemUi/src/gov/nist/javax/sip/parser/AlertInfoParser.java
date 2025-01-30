package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.AlertInfoList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AlertInfoParser extends ParametersParser {
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
