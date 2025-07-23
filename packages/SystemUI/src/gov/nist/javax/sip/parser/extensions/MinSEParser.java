package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.MinSE;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MinSEParser extends ParametersParser {
    public MinSEParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        MinSE minSE = new MinSE();
        headerName(2134);
        try {
            minSE.setExpires(Integer.parseInt(this.lexer.ttoken()));
            this.lexer.SPorHT();
            parse(minSE);
            return minSE;
        } catch (NumberFormatException unused) {
            throw createParseException("bad integer format");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public MinSEParser(Lexer lexer) {
        super(lexer);
    }
}
