package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PChargingFunctionAddresses extends ParametersHeader implements Header {
    public PChargingFunctionAddresses() {
        super("P-Charging-Function-Addresses");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.duplicates.isEmpty()) {
            stringBuffer.append(this.duplicates.encode());
        }
        return stringBuffer.toString();
    }
}
