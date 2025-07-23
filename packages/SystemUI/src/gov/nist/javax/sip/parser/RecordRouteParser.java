package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RecordRouteParser extends AddressParametersParser {
    public RecordRouteParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        char lookAhead;
        RecordRouteList recordRouteList = new RecordRouteList();
        this.lexer.match(2092);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        while (true) {
            RecordRoute recordRoute = new RecordRoute();
            parse((AddressParametersHeader) recordRoute);
            recordRouteList.add((SIPHeader) recordRoute);
            this.lexer.SPorHT();
            lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (lookAhead == '\n') {
            return recordRouteList;
        }
        throw createParseException("unexpected char");
    }

    public RecordRouteParser(Lexer lexer) {
        super(lexer);
    }
}
