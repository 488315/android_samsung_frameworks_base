package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.TimeStamp;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class TimeStampParser extends HeaderParser {
    public TimeStampParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        TimeStamp timeStamp = new TimeStamp();
        headerName(2103);
        timeStamp.setHeaderName("Timestamp");
        this.lexer.SPorHT();
        String strNumber = this.lexer.number();
        try {
            if (this.lexer.lookAhead(0) == '.') {
                this.lexer.match(46);
                timeStamp.setTimeStamp(Float.parseFloat(strNumber + "." + this.lexer.number()));
            } else {
                timeStamp.setTime(Long.parseLong(strNumber));
            }
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == '\n') {
                return timeStamp;
            }
            String strNumber2 = this.lexer.number();
            try {
                if (this.lexer.lookAhead(0) != '.') {
                    timeStamp.setDelay(Integer.parseInt(strNumber2));
                    return timeStamp;
                }
                this.lexer.match(46);
                timeStamp.setDelay(Float.parseFloat(strNumber2 + "." + this.lexer.number()));
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
