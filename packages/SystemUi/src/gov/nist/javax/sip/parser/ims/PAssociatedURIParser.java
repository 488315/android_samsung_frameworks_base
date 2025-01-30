package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PAssociatedURIList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PAssociatedURIParser extends AddressParametersParser {
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
