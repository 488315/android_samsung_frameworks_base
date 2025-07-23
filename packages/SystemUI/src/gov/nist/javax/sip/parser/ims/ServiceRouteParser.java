package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.ServiceRoute;
import gov.nist.javax.sip.header.ims.ServiceRouteList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ServiceRouteParser extends AddressParametersParser {
    public ServiceRouteParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ServiceRouteList serviceRouteList = new ServiceRouteList();
        this.lexer.match(2120);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        while (true) {
            ServiceRoute serviceRoute = new ServiceRoute();
            parse((AddressParametersHeader) serviceRoute);
            serviceRouteList.add((SIPHeader) serviceRoute);
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) != ',') {
                break;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        if (this.lexer.lookAhead(0) == '\n') {
            return serviceRouteList;
        }
        throw createParseException("unexpected char");
    }

    public ServiceRouteParser(Lexer lexer) {
        super(lexer);
    }
}
