package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.ContentLanguage;
import gov.nist.javax.sip.header.ContentLanguageList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContentLanguageParser extends HeaderParser {
    public ContentLanguageParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ContentLanguageList contentLanguageList = new ContentLanguageList();
        try {
            headerName(2075);
            while (this.lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                this.lexer.match(4095);
                ContentLanguage contentLanguage = new ContentLanguage(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                contentLanguageList.add((SIPHeader) contentLanguage);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    this.lexer.SPorHT();
                    ContentLanguage contentLanguage2 = new ContentLanguage(this.lexer.currentMatch.tokenValue);
                    this.lexer.SPorHT();
                    contentLanguageList.add((SIPHeader) contentLanguage2);
                }
            }
            return contentLanguageList;
        } catch (ParseException e) {
            throw createParseException(e.getMessage());
        }
    }

    public ContentLanguageParser(Lexer lexer) {
        super(lexer);
    }
}
