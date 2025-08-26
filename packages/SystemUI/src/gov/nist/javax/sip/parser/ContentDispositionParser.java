package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentDisposition;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ContentDispositionParser extends ParametersParser {
    public ContentDispositionParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
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
