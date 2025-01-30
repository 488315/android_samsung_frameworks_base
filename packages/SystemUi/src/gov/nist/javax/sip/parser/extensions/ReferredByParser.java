package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.ReferredBy;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReferredByParser extends AddressParametersParser {
    public ReferredByParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2132);
        ReferredBy referredBy = new ReferredBy();
        parse((AddressParametersHeader) referredBy);
        this.lexer.match(10);
        return referredBy;
    }

    public ReferredByParser(Lexer lexer) {
        super(lexer);
    }
}
