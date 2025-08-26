package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Join;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class JoinParser extends ParametersParser {
    public JoinParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2140);
        Join join = new Join();
        this.lexer.SPorHT();
        String strByteStringNoSemicolon = this.lexer.byteStringNoSemicolon();
        this.lexer.SPorHT();
        parse(join);
        join.callId = strByteStringNoSemicolon;
        return join;
    }

    public JoinParser(Lexer lexer) {
        super(lexer);
    }
}
