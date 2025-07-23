package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FromParser extends AddressParametersParser {
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
