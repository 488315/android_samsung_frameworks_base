package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PAssociatedURIList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PAssociatedURIParser extends AddressParametersParser {
    public PAssociatedURIParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        PAssociatedURIList pAssociatedURIList = new PAssociatedURIList();
        headerName(2129);
        PAssociatedURI pAssociatedURI = new PAssociatedURI();
        pAssociatedURI.setHeaderName("P-Associated-URI");
        parse((AddressParametersHeader) pAssociatedURI);
        pAssociatedURIList.add((SIPHeader) pAssociatedURI);
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            PAssociatedURI pAssociatedURI2 = new PAssociatedURI();
            parse((AddressParametersHeader) pAssociatedURI2);
            pAssociatedURIList.add((SIPHeader) pAssociatedURI2);
            this.lexer.SPorHT();
        }
        this.lexer.SPorHT();
        this.lexer.match(10);
        return pAssociatedURIList;
    }

    public PAssociatedURIParser(Lexer lexer) {
        super(lexer);
    }
}
