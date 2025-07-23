package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.References;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ReferencesParser extends ParametersParser {
    public ReferencesParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2146);
        References references = new References();
        this.lexer.SPorHT();
        references.setCallId(this.lexer.byteStringNoSemicolon());
        parse(references);
        return references;
    }

    public ReferencesParser(Lexer lexer) {
        super(lexer);
    }
}
