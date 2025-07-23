package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingVector;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PChargingVectorParser extends ParametersParser {
    public PChargingVectorParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2125);
        PChargingVector pChargingVector = new PChargingVector();
        while (this.lexer.lookAhead(0) != '\n') {
            pChargingVector.setParameter(nameValue());
            this.lexer.SPorHT();
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead == '\n' || lookAhead == 0) {
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
