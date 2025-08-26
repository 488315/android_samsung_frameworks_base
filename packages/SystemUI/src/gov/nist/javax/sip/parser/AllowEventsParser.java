package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AllowEvents;
import gov.nist.javax.sip.header.AllowEventsList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class AllowEventsParser extends HeaderParser {
    public AllowEventsParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        AllowEventsList allowEventsList = new AllowEventsList();
        headerName(2113);
        AllowEvents allowEvents = new AllowEvents();
        allowEvents.setHeaderName("Allow-Events");
        this.lexer.SPorHT();
        this.lexer.match(4095);
        allowEvents.setEventType(this.lexer.currentMatch.tokenValue);
        allowEventsList.add((SIPHeader) allowEvents);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            AllowEvents allowEvents2 = new AllowEvents();
            this.lexer.match(4095);
            allowEvents2.setEventType(this.lexer.currentMatch.tokenValue);
            allowEventsList.add((SIPHeader) allowEvents2);
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        this.lexer.match(10);
        return allowEventsList;
    }

    public AllowEventsParser(Lexer lexer) {
        super(lexer);
    }
}
