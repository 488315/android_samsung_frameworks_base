package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Subject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SubjectParser extends HeaderParser {
    public SubjectParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        Subject subject = new Subject();
        headerName(2085);
        this.lexer.SPorHT();
        subject.setSubject(this.lexer.getRest().trim());
        return subject;
    }

    public SubjectParser(Lexer lexer) {
        super(lexer);
    }
}
