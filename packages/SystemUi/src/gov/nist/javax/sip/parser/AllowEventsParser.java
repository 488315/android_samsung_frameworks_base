package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AllowEvents;
import gov.nist.javax.sip.header.AllowEventsList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AllowEventsParser extends HeaderParser {
    public AllowEventsParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
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
