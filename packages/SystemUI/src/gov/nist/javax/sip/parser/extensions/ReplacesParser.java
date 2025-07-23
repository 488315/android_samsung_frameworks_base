package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Replaces;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReplacesParser extends ParametersParser {
    public ReplacesParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2135);
        Replaces replaces = new Replaces();
        this.lexer.SPorHT();
        String byteStringNoSemicolon = this.lexer.byteStringNoSemicolon();
        this.lexer.SPorHT();
        parse(replaces);
        replaces.callId = byteStringNoSemicolon;
        return replaces;
    }

    public ReplacesParser(Lexer lexer) {
        super(lexer);
    }
}
