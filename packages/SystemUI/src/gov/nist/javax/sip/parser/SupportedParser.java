package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Supported;
import gov.nist.javax.sip.header.SupportedList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SupportedParser extends HeaderParser {
    public SupportedParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        SupportedList supportedList = new SupportedList();
        headerName(2068);
        while (this.lexer.lookAhead(0) != '\n') {
            this.lexer.SPorHT();
            Supported supported = new Supported();
            supported.setHeaderName("Supported");
            this.lexer.match(4095);
            supported.setOptionTag(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            supportedList.add((SIPHeader) supported);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                Supported supported2 = new Supported();
                this.lexer.match(4095);
                supported2.setOptionTag(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                supportedList.add((SIPHeader) supported2);
            }
        }
        return supportedList;
    }

    public SupportedParser(Lexer lexer) {
        super(lexer);
    }
}
