package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class RouteParser extends AddressParametersParser {
    public RouteParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        char cLookAhead;
        RouteList routeList = new RouteList();
        this.lexer.match(2070);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        while (true) {
            Route route = new Route();
            parse((AddressParametersHeader) route);
            routeList.add((SIPHeader) route);
            this.lexer.SPorHT();
            cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (cLookAhead == '\n') {
            return routeList;
        }
        throw createParseException("unexpected char");
    }

    public RouteParser(Lexer lexer) {
        super(lexer);
    }
}
