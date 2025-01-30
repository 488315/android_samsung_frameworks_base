package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MimeVersion;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MimeVersionParser extends HeaderParser {
    public MimeVersionParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
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
