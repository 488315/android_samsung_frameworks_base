package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.SessionExpires;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SessionExpiresParser extends ParametersParser {
    public SessionExpiresParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        SessionExpires sessionExpires = new SessionExpires();
        headerName(2133);
        try {
            sessionExpires.setExpires(Integer.parseInt(this.lexer.ttoken()));
            this.lexer.SPorHT();
            parse(sessionExpires);
            return sessionExpires;
        } catch (NumberFormatException unused) {
            throw createParseException("bad integer format");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public SessionExpiresParser(Lexer lexer) {
        super(lexer);
    }
}
