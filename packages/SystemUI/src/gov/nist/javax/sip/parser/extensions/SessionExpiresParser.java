package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.SessionExpires;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SessionExpiresParser extends ParametersParser {
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
