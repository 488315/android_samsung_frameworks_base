package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FromParser extends AddressParametersParser {
    public FromParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        From from = new From();
        this.lexer.match(2062);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        parse((AddressParametersHeader) from);
        this.lexer.match(10);
        return from;
    }

    public FromParser(Lexer lexer) {
        super(lexer);
    }
}
