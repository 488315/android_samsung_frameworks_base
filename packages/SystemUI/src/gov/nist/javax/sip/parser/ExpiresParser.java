package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class ExpiresParser extends HeaderParser {
    public ExpiresParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        Expires expires = new Expires();
        this.lexer.match(2090);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        String strTtoken = this.lexer.ttoken();
        this.lexer.match(10);
        try {
            expires.setExpires(Integer.parseInt(strTtoken));
            return expires;
        } catch (NumberFormatException unused) {
            throw createParseException("bad integer format");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public ExpiresParser(Lexer lexer) {
        super(lexer);
    }
}
