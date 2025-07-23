package gov.nist.javax.sip.parser;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.SIPDateHeader;
import gov.nist.javax.sip.header.SIPHeader;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DateParser extends HeaderParser {
    public DateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2080);
        ParserCore.dbg_enter();
        try {
            String lowerCase = this.lexer.ttoken().toLowerCase();
            if (!"Mon".equalsIgnoreCase(lowerCase) && !"Tue".equalsIgnoreCase(lowerCase) && !"Wed".equalsIgnoreCase(lowerCase) && !"Thu".equalsIgnoreCase(lowerCase) && !"Fri".equalsIgnoreCase(lowerCase) && !"Sat".equalsIgnoreCase(lowerCase) && !"Sun".equalsIgnoreCase(lowerCase)) {
                throw createParseException("bad wkday");
            }
            this.lexer.match(44);
            this.lexer.match(32);
            try {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                int parseInt = Integer.parseInt(this.lexer.number());
                if (parseInt <= 0 || parseInt > 31) {
                    throw createParseException("Bad day ");
                }
                calendar.set(5, parseInt);
                this.lexer.match(32);
                String lowerCase2 = this.lexer.ttoken().toLowerCase();
                if (lowerCase2.equals("jan")) {
                    calendar.set(2, 0);
                } else if (lowerCase2.equals("feb")) {
                    calendar.set(2, 1);
                } else if (lowerCase2.equals("mar")) {
                    calendar.set(2, 2);
                } else if (lowerCase2.equals("apr")) {
                    calendar.set(2, 3);
                } else if (lowerCase2.equals("may")) {
                    calendar.set(2, 4);
                } else if (lowerCase2.equals("jun")) {
                    calendar.set(2, 5);
                } else if (lowerCase2.equals("jul")) {
                    calendar.set(2, 6);
                } else if (lowerCase2.equals("aug")) {
                    calendar.set(2, 7);
                } else if (lowerCase2.equals("sep")) {
                    calendar.set(2, 8);
                } else if (lowerCase2.equals("oct")) {
                    calendar.set(2, 9);
                } else if (lowerCase2.equals("nov")) {
                    calendar.set(2, 10);
                } else if (lowerCase2.equals("dec")) {
                    calendar.set(2, 11);
                }
                this.lexer.match(32);
                calendar.set(1, Integer.parseInt(this.lexer.number()));
                this.lexer.match(32);
                try {
                    calendar.set(11, Integer.parseInt(this.lexer.number()));
                    this.lexer.match(58);
                    calendar.set(12, Integer.parseInt(this.lexer.number()));
                    this.lexer.match(58);
                    calendar.set(13, Integer.parseInt(this.lexer.number()));
                    this.lexer.match(32);
                    String lowerCase3 = this.lexer.ttoken().toLowerCase();
                    if (!"gmt".equals(lowerCase3)) {
                        throw createParseException("Bad Time Zone " + lowerCase3);
                    }
                    this.lexer.match(10);
                    SIPDateHeader sIPDateHeader = new SIPDateHeader();
                    sIPDateHeader.setDate(calendar);
                    return sIPDateHeader;
                } catch (Exception unused) {
                    throw createParseException("error processing time ");
                }
            } catch (Exception unused2) {
                throw createParseException("bad date field");
            }
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public DateParser(Lexer lexer) {
        super(lexer);
    }
}
