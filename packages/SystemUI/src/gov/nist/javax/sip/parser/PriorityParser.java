package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class PriorityParser extends HeaderParser {
    public PriorityParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
