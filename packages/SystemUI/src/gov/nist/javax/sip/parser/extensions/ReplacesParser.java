package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Replaces;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ReplacesParser extends ParametersParser {
    public ReplacesParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2135);
        Replaces replaces = new Replaces();
        this.lexer.SPorHT();
        String strByteStringNoSemicolon = this.lexer.byteStringNoSemicolon();
        this.lexer.SPorHT();
        parse(replaces);
        replaces.callId = strByteStringNoSemicolon;
        return replaces;
    }

    public ReplacesParser(Lexer lexer) {
        super(lexer);
    }
}
