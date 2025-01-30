package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentDisposition;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContentDispositionParser extends ParametersParser {
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
