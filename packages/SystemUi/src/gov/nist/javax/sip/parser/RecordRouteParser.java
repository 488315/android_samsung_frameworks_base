package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RecordRouteParser extends AddressParametersParser {
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
