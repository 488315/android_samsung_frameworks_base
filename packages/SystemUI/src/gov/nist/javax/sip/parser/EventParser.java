package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class EventParser extends ParametersParser {
    public EventParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
