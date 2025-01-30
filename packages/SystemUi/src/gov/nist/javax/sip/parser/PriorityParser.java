package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PriorityParser extends HeaderParser {
    public PriorityParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        Priority priority = new Priority();
        headerName(2081);
        priority.setHeaderName("Priority");
        this.lexer.SPorHT();
        priority.setPriority(this.lexer.ttokenSafe());
        this.lexer.SPorHT();
        this.lexer.match(10);
        return priority;
    }

    public PriorityParser(Lexer lexer) {
        super(lexer);
    }
}
