package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Join;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class JoinParser extends ParametersParser {
    public JoinParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2140);
        Join join = new Join();
        this.lexer.SPorHT();
        String byteStringNoSemicolon = this.lexer.byteStringNoSemicolon();
        this.lexer.SPorHT();
        parse(join);
        join.callId = byteStringNoSemicolon;
        return join;
    }

    public JoinParser(Lexer lexer) {
        super(lexer);
    }
}
