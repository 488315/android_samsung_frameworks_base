package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentDisposition;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentDispositionParser extends ParametersParser {
    public ContentDispositionParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        try {
            headerName(2100);
            ContentDisposition contentDisposition = new ContentDisposition();
            contentDisposition.setHeaderName("Content-Disposition");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            contentDisposition.setDispositionType(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            parse(contentDisposition);
            this.lexer.SPorHT();
            this.lexer.match(10);
            return contentDisposition;
        } catch (ParseException e) {
            throw createParseException(e.getMessage());
        }
    }

    public ContentDispositionParser(Lexer lexer) {
        super(lexer);
    }
}
