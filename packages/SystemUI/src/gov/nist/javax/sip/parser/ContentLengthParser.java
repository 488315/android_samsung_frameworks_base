package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentLengthParser extends HeaderParser {
    public ContentLengthParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        try {
            ContentLength contentLength = new ContentLength();
            headerName(2084);
            contentLength.setContentLength(Integer.parseInt(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return contentLength;
        } catch (NumberFormatException e) {
            throw createParseException(e.getMessage());
        } catch (InvalidArgumentException e2) {
            throw createParseException(e2.getMessage());
        }
    }

    public ContentLengthParser(Lexer lexer) {
        super(lexer);
    }
}
