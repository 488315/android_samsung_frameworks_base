package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentity;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PPreferredIdentityParser extends AddressParametersParser {
    public PPreferredIdentityParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        this.lexer.match(2122);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        PPreferredIdentity pPreferredIdentity = new PPreferredIdentity();
        parse((AddressParametersHeader) pPreferredIdentity);
        return pPreferredIdentity;
    }

    public PPreferredIdentityParser(Lexer lexer) {
        super(lexer);
    }
}
