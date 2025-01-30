package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EventParser extends ParametersParser {
    public EventParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        try {
            headerName(2111);
            this.lexer.SPorHT();
            Event event = new Event();
            this.lexer.match(4095);
            event.setEventType(this.lexer.currentMatch.tokenValue);
            parse(event);
            this.lexer.SPorHT();
            this.lexer.match(10);
            return event;
        } catch (ParseException e) {
            throw createParseException(e.getMessage());
        }
    }

    public EventParser(Lexer lexer) {
        super(lexer);
    }
}
