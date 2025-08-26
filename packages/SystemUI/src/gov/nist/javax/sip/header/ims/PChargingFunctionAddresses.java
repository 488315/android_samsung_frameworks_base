package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.header.Header;

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
