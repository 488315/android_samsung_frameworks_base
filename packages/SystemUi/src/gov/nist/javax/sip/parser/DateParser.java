package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPDateHeader;
import gov.nist.javax.sip.header.SIPHeader;
import java.util.Calendar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DateParser extends HeaderParser {
    public DateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2080);
        wkday();
        this.lexer.match(44);
        this.lexer.match(32);
        Calendar date = date();
        this.lexer.match(32);
        time(date);
        this.lexer.match(32);
        String lowerCase = this.lexer.ttoken().toLowerCase();
        if (!"gmt".equals(lowerCase)) {
            throw createParseException("Bad Time Zone " + lowerCase);
        }
        this.lexer.match(10);
        SIPDateHeader sIPDateHeader = new SIPDateHeader();
        sIPDateHeader.setDate(date);
        return sIPDateHeader;
    }

    public DateParser(Lexer lexer) {
        super(lexer);
    }
}
