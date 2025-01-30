package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Replaces;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReplacesParser extends ParametersParser {
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
