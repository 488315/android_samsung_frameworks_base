package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PCalledPartyID;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PCalledPartyIDParser extends AddressParametersParser {
    public PCalledPartyIDParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        this.lexer.match(2128);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        PCalledPartyID pCalledPartyID = new PCalledPartyID();
        parse((AddressParametersHeader) pCalledPartyID);
        return pCalledPartyID;
    }

    public PCalledPartyIDParser(Lexer lexer) {
        super(lexer);
    }
}
