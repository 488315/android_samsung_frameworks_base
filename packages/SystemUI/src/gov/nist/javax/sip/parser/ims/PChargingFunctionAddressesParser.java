package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddresses;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class PChargingFunctionAddressesParser extends ParametersParser {
    public PChargingFunctionAddressesParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        headerName(2124);
        PChargingFunctionAddresses pChargingFunctionAddresses = new PChargingFunctionAddresses();
        while (this.lexer.lookAhead(0) != '\n') {
            pChargingFunctionAddresses.setMultiParameter(nameValue());
            this.lexer.SPorHT();
            char cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead == '\n' || cLookAhead == 0) {
                break;
            }
            this.lexer.match(59);
            this.lexer.SPorHT();
        }
        parse(pChargingFunctionAddresses);
        return pChargingFunctionAddresses;
    }

    public PChargingFunctionAddressesParser(Lexer lexer) {
        super(lexer);
    }
}
