package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EventParser extends ParametersParser {
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
