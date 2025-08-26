package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MimeVersion;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

/* loaded from: classes4.dex */
public class MimeVersionParser extends HeaderParser {
    public MimeVersionParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        MimeVersion mimeVersion = new MimeVersion();
        headerName(2060);
        mimeVersion.setHeaderName("MIME-Version");
        try {
            mimeVersion.setMajorVersion(Integer.parseInt(this.lexer.number()));
            this.lexer.match(46);
            mimeVersion.setMinorVersion(Integer.parseInt(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return mimeVersion;
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public MimeVersionParser(Lexer lexer) {
        super(lexer);
    }
}
