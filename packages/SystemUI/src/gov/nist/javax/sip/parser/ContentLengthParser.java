package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class ContentLengthParser extends HeaderParser {
    public ContentLengthParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
