package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Subject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SubjectParser extends HeaderParser {
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
