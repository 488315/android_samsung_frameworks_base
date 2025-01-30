package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Supported;
import gov.nist.javax.sip.header.SupportedList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SupportedParser extends HeaderParser {
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
