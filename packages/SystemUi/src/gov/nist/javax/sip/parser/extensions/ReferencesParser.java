package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.References;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReferencesParser extends ParametersParser {
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
