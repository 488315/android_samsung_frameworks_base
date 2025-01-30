package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.C4830To;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ToParser extends AddressParametersParser {
    public ToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2063);
        C4830To c4830To = new C4830To();
        parse((AddressParametersHeader) c4830To);
        this.lexer.match(10);
        return c4830To;
    }

    public ToParser(Lexer lexer) {
        super(lexer);
    }
}
