package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RouteParser extends AddressParametersParser {
    public RouteParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        char lookAhead;
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
            lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (lookAhead == '\n') {
            return routeList;
        }
        throw createParseException("unexpected char");
    }

    public RouteParser(Lexer lexer) {
        super(lexer);
    }
}
