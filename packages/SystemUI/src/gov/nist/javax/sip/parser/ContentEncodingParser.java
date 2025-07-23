package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentEncoding;
import gov.nist.javax.sip.header.ContentEncodingList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentEncodingParser extends HeaderParser {
    public ContentEncodingParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ContentEncodingList contentEncodingList = new ContentEncodingList();
        try {
            headerName(2083);
            while (this.lexer.lookAhead(0) != '\n') {
                ContentEncoding contentEncoding = new ContentEncoding();
                contentEncoding.setHeaderName("Content-Encoding");
                this.lexer.SPorHT();
                this.lexer.match(4095);
                contentEncoding.setEncoding(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                contentEncodingList.add((SIPHeader) contentEncoding);
                while (this.lexer.lookAhead(0) == ',') {
                    ContentEncoding contentEncoding2 = new ContentEncoding();
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    this.lexer.SPorHT();
                    contentEncoding2.setEncoding(this.lexer.currentMatch.tokenValue);
                    this.lexer.SPorHT();
                    contentEncodingList.add((SIPHeader) contentEncoding2);
                }
            }
            return contentEncodingList;
        } catch (ParseException e) {
            throw createParseException(e.getMessage());
        }
    }

    public ContentEncodingParser(Lexer lexer) {
        super(lexer);
    }
}
