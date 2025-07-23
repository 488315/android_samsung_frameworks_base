package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.Privacy;
import gov.nist.javax.sip.header.ims.PrivacyList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PrivacyParser extends HeaderParser {
    public PrivacyParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        PrivacyList privacyList = new PrivacyList();
        headerName(2126);
        while (this.lexer.lookAhead(0) != '\n') {
            this.lexer.SPorHT();
            Privacy privacy = new Privacy();
            privacy.setHeaderName("Privacy");
            this.lexer.match(4095);
            privacy.setPrivacy(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            privacyList.add((SIPHeader) privacy);
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                Privacy privacy2 = new Privacy();
                this.lexer.match(4095);
                privacy2.setPrivacy(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                privacyList.add((SIPHeader) privacy2);
            }
        }
        return privacyList;
    }

    public PrivacyParser(Lexer lexer) {
        super(lexer);
    }
}
