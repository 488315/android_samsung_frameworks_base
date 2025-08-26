package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingVector;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class PChargingVectorParser extends ParametersParser {
    public PChargingVectorParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2125);
        PChargingVector pChargingVector = new PChargingVector();
        while (this.lexer.lookAhead(0) != '\n') {
            pChargingVector.setParameter(nameValue());
            this.lexer.SPorHT();
            char cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead == '\n' || cLookAhead == 0) {
                break;
            }
            this.lexer.match(59);
            this.lexer.SPorHT();
        }
        parse(pChargingVector);
        if (pChargingVector.getParameter("icid-value") != null) {
            return pChargingVector;
        }
        throw new ParseException("Missing a required Parameter : icid-value", 0);
    }

    public PChargingVectorParser(Lexer lexer) {
        super(lexer);
    }
}
