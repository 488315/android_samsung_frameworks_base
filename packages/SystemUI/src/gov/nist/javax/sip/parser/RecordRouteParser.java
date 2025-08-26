package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class RecordRouteParser extends AddressParametersParser {
    public RecordRouteParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        char cLookAhead;
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
            cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (cLookAhead == '\n') {
            return recordRouteList;
        }
        throw createParseException("unexpected char");
    }

    public RecordRouteParser(Lexer lexer) {
        super(lexer);
    }
}
