package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddresses;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PChargingFunctionAddressesParser extends ParametersParser {
    public PChargingFunctionAddressesParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2124);
        PChargingFunctionAddresses pChargingFunctionAddresses = new PChargingFunctionAddresses();
        while (this.lexer.lookAhead(0) != '\n') {
            pChargingFunctionAddresses.setMultiParameter(nameValue());
            this.lexer.SPorHT();
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead == '\n' || lookAhead == 0) {
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
