package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReferTo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReferToParser extends AddressParametersParser {
    public ReferToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2114);
        ReferTo referTo = new ReferTo();
        parse((AddressParametersHeader) referTo);
        this.lexer.match(10);
        return referTo;
    }

    public ReferToParser(Lexer lexer) {
        super(lexer);
    }
}
