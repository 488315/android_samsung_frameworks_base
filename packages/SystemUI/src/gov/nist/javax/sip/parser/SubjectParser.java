package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Subject;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class SubjectParser extends HeaderParser {
    public SubjectParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
