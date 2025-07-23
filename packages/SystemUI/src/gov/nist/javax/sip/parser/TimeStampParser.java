package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.TimeStamp;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TimeStampParser extends HeaderParser {
    public TimeStampParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        TimeStamp timeStamp = new TimeStamp();
        headerName(2103);
        timeStamp.setHeaderName("Timestamp");
        this.lexer.SPorHT();
        String number = this.lexer.number();
        try {
            if (this.lexer.lookAhead(0) == '.') {
                this.lexer.match(46);
                timeStamp.setTimeStamp(Float.parseFloat(number + "." + this.lexer.number()));
            } else {
                timeStamp.setTime(Long.parseLong(number));
            }
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == '\n') {
                return timeStamp;
            }
            String number2 = this.lexer.number();
            try {
                if (this.lexer.lookAhead(0) != '.') {
                    timeStamp.setDelay(Integer.parseInt(number2));
                    return timeStamp;
                }
                this.lexer.match(46);
                timeStamp.setDelay(Float.parseFloat(number2 + "." + this.lexer.number()));
                return timeStamp;
            } catch (NumberFormatException e) {
                throw createParseException(e.getMessage());
            } catch (InvalidArgumentException e2) {
                throw createParseException(e2.getMessage());
            }
        } catch (NumberFormatException e3) {
            throw createParseException(e3.getMessage());
        } catch (InvalidArgumentException e4) {
            throw createParseException(e4.getMessage());
        }
    }

    public TimeStampParser(Lexer lexer) {
        super(lexer);
    }
}
